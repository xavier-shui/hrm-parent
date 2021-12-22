package cn.xavier.hrm.feign;

import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@Component
public class CourseDocFallbackFactory implements FallbackFactory<ICourseDocFeignClient> {
    @Override
    public ICourseDocFeignClient create(Throwable cause) {
        return new ICourseDocFeignClient() {
            @Override
            public AjaxResult onlineCourse(List<CourseDoc> courseDocs) {
                return AjaxResult.me()
                        .setSuccess(false)
                        .setMessage("课程上架失败");
            }

            @Override
            public AjaxResult offlineCourse(List<CourseDoc> courseDocs) {
                return AjaxResult.me()
                        .setSuccess(false)
                        .setMessage("课程下架失败");
            }
        };
    }
}
