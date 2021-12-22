package cn.xavier.hrm.dto;

import cn.xavier.hrm.domain.Course;
import cn.xavier.hrm.domain.CourseDetail;
import cn.xavier.hrm.domain.CourseMarket;
import lombok.Data;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@Data
public class CourseDto {
    private Course course;
    private CourseMarket courseMarket;
    private CourseDetail courseDetail;
}
