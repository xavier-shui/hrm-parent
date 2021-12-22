package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.Course;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
public interface CourseMapper extends BaseMapper<Course> {

    void batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
