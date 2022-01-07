package cn.xavier.hrm.task;

import cn.xavier.hrm.domain.KillCourse;
import cn.xavier.hrm.mapper.KillCourseMapper;
import cn.xavier.hrm.util.DateUtil;
import cn.xavier.hrm.util.StrUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class KillCourseTask {

    @Autowired
    private KillCourseMapper killCourseMapper ;

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private RedissonClient redissonClient ;


    //课程定时发布，每天上一次
    @Scheduled(cron = "0/5 * * * * *")
    @Async
    public void killCoursePublish(){
        log.info("定时发布课程，线程：{}",Thread.currentThread().getName());

        //1.查询近2天课程
        String startTime = DateUtil.toStartForNow();
        String endTime = DateUtil.toEndForPlus(2);
        List<KillCourse> killCourseList = killCourseMapper
                .selectList(new EntityWrapper<KillCourse>()
                        .between("start_time", startTime, endTime)
                        .eq("kill_status",0));
        //判断空
        if(killCourseList.isEmpty()) return;

        //最近两天秒杀的课程批量存储到Redis
        Map<String,KillCourse> toRedisMap = new HashMap<>((int)(killCourseList.size()*1.5));

        //绑定要给hash
        BoundHashOperations killcourseOperations = redisTemplate.boundHashOps("killcourse");

        for (KillCourse killCourses : killCourseList){

            //2.相同的秒杀课程不能重复发布
            if(killcourseOperations.keys().contains(killCourses.getId().toString())){
                log.info("课程重复发布 ：{}",killCourses.getCourseName());
                continue;
            }

            killCourses.setKillStatus(KillCourse.STATE_KILLING);
            //3.设置一个秒杀随机码
            killCourses.setKillCode(StrUtils.getRandomString(16));
            //更改发布课程状态
            Integer integer = killCourseMapper.updateById(killCourses);
            if(integer != null && integer.intValue() == 1){

                //4.库存预热
                RSemaphore semaphore = redissonClient.getSemaphore("store:" + killCourses.getSessionNumber() + ":" + killCourses.getId());
                boolean trySetPermitsResult = semaphore.trySetPermits(killCourses.getKillCount());
                //5.存储到Redis
                log.info("发布课程：{}",killCourses.getCourseName());
                toRedisMap.put(killCourses.getId().toString() , killCourses);
            }
        }

        //存储到Redis
        if(!toRedisMap.isEmpty()){
            killcourseOperations.putAll(toRedisMap);
        }
    }
}
