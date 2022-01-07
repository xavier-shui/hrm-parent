package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.KillCourse;
import cn.xavier.hrm.dto.KillDto;
import cn.xavier.hrm.exception.HrmException;
import cn.xavier.hrm.mapper.KillCourseMapper;
import cn.xavier.hrm.service.IKillCourseService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.IdWorker;
import cn.xavier.hrm.util.ValidUtils;
import cn.xavier.hrm.vo.KillOrderVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static cn.xavier.hrm.constant.MQConstants.KILL_EXCHANGE_NAME_TOPIC;
import static cn.xavier.hrm.constant.MQConstants.KILL_ROUTINGKEY_ORDER;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
@Service
@Slf4j
public class KillCourseServiceImpl extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {

    private IdWorker idWorker = IdWorker.getInstance();

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private RedissonClient redissonClient ;

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Override
    public AjaxResult add(KillCourse killCourse) {
        //1.判断参数
        Long number = idWorker.nextId();

        killCourse.setSessionNumber(number.toString());
        killCourse.setKillLimit(1);
        killCourse.setKillStatus(KillCourse.STATE_UN_PUBLISH);
        killCourse.setCreateTime(new Date());
        //2.保存数据
        baseMapper.insert(killCourse);
        return AjaxResult.me() ;
    }

    @Override
    public AjaxResult publishs() {
        //绑定要给hash
        BoundHashOperations killcourseOperations = redisTemplate.boundHashOps("killcourse");
        return AjaxResult.me().setResultObj(killcourseOperations.values());
    }

    @Override
    public AjaxResult publishsOne(Long id) {
        //绑定要给hash
        BoundHashOperations killcourseOperations = redisTemplate.boundHashOps("killcourse");
        return AjaxResult.me().setResultObj(killcourseOperations.get(id.toString()));
    }

    @Override
    public AjaxResult kill(KillDto dto) {
        long time = System.currentTimeMillis();
        //1.参数
        //参数判断
       // String killCode = dto.getKillCode();
        Long killCourseId = dto.getKillCourseId();

       // ValidUtils.assertNotNull(killCode,"秒杀码不可空");
        ValidUtils.assertNotNull(killCourseId,"秒杀课程无效");

        //登陆判断 //todo:测试代码，应该从UserContextHolder中取用户
        //Long userId = dto.getUserId();
        Long userId = Long.valueOf(ThreadLocalRandom.current().nextInt(100, 1000));
        //取出课程，
        BoundHashOperations killcourseOperations = redisTemplate.boundHashOps("killcourse");
        KillCourse killCourse = (KillCourse)killcourseOperations.get(killCourseId.toString());
        ValidUtils.assertNotNull(killCourse,"秒杀课程无效");

        //判断随机码
       // AssertUtils.assertEuqal(killCode,killCourse.getKillCode(),"无效的秒杀码");

        //判断时间范围
        Date now = new Date();
        if(now.before(killCourse.getStartTime()) || now.after(killCourse.getEndTime())){
            throw  new HrmException("秒杀时间无效");
        }
        //TODO ：单个用户ID不能重复秒杀
        Boolean hasKey = redisTemplate.hasKey("killlog:"+userId+":"+killCourseId);
        ValidUtils.isTrue(!hasKey,"你已经秒杀过了");

        //2.判断库存，预减库存
        RSemaphore semaphore = redissonClient.getSemaphore("store:" + killCourse.getSessionNumber() + ":" + killCourse.getId());
        boolean killSuccess = semaphore.tryAcquire(dto.getKillCount());//默认抢购一个
        if(!killSuccess){
            throw  new HrmException("你不是单身把,手速不行...");
        }
        log.info("秒杀成功...");

        //3.异步下单
        //创建订单数据
        String orderSN = Long.valueOf(idWorker.nextId()).toString();
        KillOrderVo vo = new KillOrderVo(killCourseId,
                killCourse.getKillPrice(),
                userId,orderSN,
                killCourse.getSessionNumber(),killCourse.getCourseName(),dto.getKillCount());

        //往MQ发送订单消息
        rabbitTemplate.convertAndSend(
                KILL_EXCHANGE_NAME_TOPIC,
                KILL_ROUTINGKEY_ORDER,
                vo);


        long bTime = killCourse.getEndTime().getTime() - killCourse.getStartTime().getTime();

        redisTemplate.opsForValue().set("killlog:"+userId+":"+killCourseId,"",bTime, TimeUnit.MILLISECONDS);

        log.info("秒杀时间：{}",System.currentTimeMillis() - time);
        System.err.println(System.currentTimeMillis() - time);
        return AjaxResult.me().setResultObj(orderSN);
    }
}
