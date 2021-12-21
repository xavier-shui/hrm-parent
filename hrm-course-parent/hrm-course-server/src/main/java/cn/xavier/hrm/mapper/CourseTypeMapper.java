package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.CourseType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程目录 Mapper 接口
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    List<CourseType> loadTreeData();
}
