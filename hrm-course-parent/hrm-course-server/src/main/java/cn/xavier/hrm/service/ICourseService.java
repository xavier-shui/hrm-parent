package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.Course;
import cn.xavier.hrm.dto.CourseDto;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
public interface ICourseService extends IService<Course> {

    AjaxResult save(CourseDto dto);

    AjaxResult onlineCourse(List<Long> ids);

    AjaxResult offlineCourse(List<Long> ids);
}
