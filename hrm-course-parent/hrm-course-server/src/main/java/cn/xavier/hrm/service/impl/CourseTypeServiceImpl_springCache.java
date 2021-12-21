/*
package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.CourseConstant;
import cn.xavier.hrm.domain.CourseType;
import cn.xavier.hrm.mapper.CourseTypeMapper;
import cn.xavier.hrm.service.ICourseTypeService;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

*/
/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 *//*

@Service
@Slf4j
public class CourseTypeServiceImpl_springCache extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    // "EL1008E: Property or field 'data' cannot be found on object of type 'org.springframework.cache.interceptor.CacheExpressionRootObject' - maybe not public or not valid?"
    @Override
    @Cacheable(cacheNames = CourseConstant.COURSE_TYPE, key = "'data'") // 有缓存从缓存拿则不执行方法，没有缓存执行方法并缓存结果
    public AjaxResult getTreeData() {
        // 循环的思想
        List<CourseType> result = new ArrayList<>(); // 封装结果
        // 查出所有，封装map
        List<CourseType> types = courseTypeMapper.selectList(null);
        Map<Long, CourseType> map = types.stream().collect(Collectors.toMap
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
        log.debug("get data from mysql database...");
        return AjaxResult.me().setResultObj(result);
    }

    // 增删改时，清空缓存，下次从数据库中拿数据
    @Override
    @CacheEvict(cacheNames = CourseConstant.COURSE_TYPE, key = "'data'")
    public boolean insert(CourseType entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(cacheNames = CourseConstant.COURSE_TYPE, key = "'data'")
    public boolean updateById(CourseType entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(cacheNames = CourseConstant.COURSE_TYPE, key = "'data'")
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }
}
*/
