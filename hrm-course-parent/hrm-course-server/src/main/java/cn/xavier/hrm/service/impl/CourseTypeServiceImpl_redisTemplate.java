package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.CourseConstant;
import cn.xavier.hrm.domain.CourseType;
import cn.xavier.hrm.mapper.CourseTypeMapper;
import cn.xavier.hrm.service.ICourseTypeService;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 * @author zhengwei-shui
 * @since 2021-12-20
 */

@Service
@Slf4j
public class CourseTypeServiceImpl_redisTemplate extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public AjaxResult getTreeData() {
        // SQL嵌套查询， 发很多条SQL，性能不高
        // List<CourseType> result = courseTypeMapper.loadTreeData();
        
        // 如果有缓存，从缓存中拿, 结束方法
        Object obj = redisTemplate.opsForValue().get(CourseConstant.COURSE_TYPE);
        if (obj != null) {
            log.debug("get data from cache...");
            return AjaxResult.me().setResultObj(obj);
        }
        // 解决缓存击穿，数据库中有，但缓存中没有时，大量请求涌过来
        if (lock.tryLock()) { // 不会阻塞线程
            try {
                // 没有缓存时从数据库查询，并保存到缓存
                // 查出所有
                List<CourseType> types = courseTypeMapper.selectList(null);
                List<CourseType> result = new ArrayList<>(); // 封装结果
                // 循环的思想,封装map
                Map<Long, CourseType> map = types.stream().collect(Collectors.toMap //之前的写法，高并发时这里报NPE
                        (CourseType::getId, self -> self));
                // 遍历，如果有父，添加到父的children
                types.forEach(type -> {
                    if (type.getPid() == 0) {
                        result.add(type);
                    } else {
                        CourseType parent = map.get(type.getPid());
                        parent.getChildren().add(type);
                    }
                });
                // 解决缓存雪崩，随机设置过期时间
                long ttl = ThreadLocalRandom.current().nextLong(100, 200);
                redisTemplate.opsForValue().set(CourseConstant.COURSE_TYPE, result, ttl, TimeUnit.MINUTES); // 缓存穿透已解决
                log.debug("get data from mysql database...");
                return AjaxResult.me().setResultObj(result);
            } finally {
                if (lock.isLocked()){
                    lock.unlock(); // 释放锁
                }
            }

        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(20L); // 防止堆栈溢出
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getTreeData(); // 继续尝试从缓存获取
        }

    }

    // 增删改时，清空缓存，下次从数据库中拿数据
    @Override
    public boolean insert(CourseType entity) {
        // 解决双写一致性，延时双删
        Boolean delete = redisTemplate.delete(CourseConstant.COURSE_TYPE);
        boolean insert = super.insert(entity);
        try {
            TimeUnit.MILLISECONDS.sleep(50L); // 等待其他线程把脏数据写入缓存
            delete = redisTemplate.delete(CourseConstant.COURSE_TYPE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!delete) { // 如果没删成功，放到消息队列

        }
        return insert;
    }

    @Override
    public boolean updateById(CourseType entity) {
        redisTemplate.delete(CourseConstant.COURSE_TYPE);
        return super.updateById(entity);
    }

    @Override
    public boolean deleteById(Serializable id) {
        redisTemplate.delete(CourseConstant.COURSE_TYPE);
        return super.deleteById(id);
    }
}
