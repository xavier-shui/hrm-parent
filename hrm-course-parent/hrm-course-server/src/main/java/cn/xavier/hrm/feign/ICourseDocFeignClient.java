package cn.xavier.hrm.feign;

import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.query.CourseDocQuery;
import cn.xavier.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@FeignClient(value = "SEARCH-SERVER", fallbackFactory = CourseDocFallbackFactory.class)
@RequestMapping("/courseDoc")
public interface ICourseDocFeignClient {
    @PostMapping("/onlineCourse")
    AjaxResult onlineCourse(@RequestBody List<CourseDoc> courseDocs);

    @PostMapping("/offlineCourse")
    AjaxResult offlineCourse(@RequestBody List<CourseDoc> courseDocs);

    @PostMapping("/queryCourses")
    AjaxResult queryCourses(@RequestBody CourseDocQuery courseDocQuery);
}
