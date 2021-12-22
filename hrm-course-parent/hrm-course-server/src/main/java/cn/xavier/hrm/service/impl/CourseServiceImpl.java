package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.CourseConstant;
import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.domain.Course;
import cn.xavier.hrm.domain.CourseDetail;
import cn.xavier.hrm.domain.CourseMarket;
import cn.xavier.hrm.dto.CourseDto;
import cn.xavier.hrm.feign.ICourseDocFeignClient;
import cn.xavier.hrm.mapper.CourseDetailMapper;
import cn.xavier.hrm.mapper.CourseMapper;
import cn.xavier.hrm.mapper.CourseMarketMapper;
import cn.xavier.hrm.mapper.CourseTypeMapper;
import cn.xavier.hrm.service.ICourseService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.ValidUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Autowired
    private ICourseDocFeignClient client;
    @Override
    public AjaxResult save(CourseDto dto) {
        // 保存三张表，共享主键，先course， 后两张表主键不是自增的
        Course course = dto.getCourse();
        courseMapper.insert(course);

        CourseDetail courseDetail = dto.getCourseDetail();
        courseDetail.setId(course.getId());
        courseDetailMapper.insert(courseDetail);

        CourseMarket courseMarket = dto.getCourseMarket();
        courseMarket.setId(course.getId());
        courseMarketMapper.insert(courseMarket);
        return AjaxResult.me();
    }

    @Override
    public AjaxResult onlineCourse(List<Long> ids) {
        ValidUtils.assertNotNull(ids, "必要参数不能为空");
        List<Course> courses = courseMapper.selectBatchIds(ids);
        // 生成courseDoc
        List<CourseDoc> courseDocs = courses.stream().map(this::course2CourseDoc).collect(Collectors.toList());
        // 保存ES
        AjaxResult result = client.onlineCourse(courseDocs);
        if (result.isSuccess()) {
            // 修改上线状态
            courseMapper.batchUpdateStatus(ids, CourseConstant.ONLINE);
        }
        return result;
    }

    @Override
    public AjaxResult offlineCourse(List<Long> ids) {
        ValidUtils.assertNotNull(ids, "必要参数不能为空");
        List<Course> courses = courseMapper.selectBatchIds(ids);
        // 生成courseDoc
        List<CourseDoc> courseDocs = courses.stream().map(this::course2CourseDoc).collect(Collectors.toList());
        // 保存ES
        AjaxResult result = client.offlineCourse(courseDocs);
        if (result.isSuccess()) {
            // 修改为下线状态
            courseMapper.batchUpdateStatus(ids, CourseConstant.OFFLINE);
        }
        return result;
    }

    private CourseDoc course2CourseDoc(Course course) {
        CourseDoc courseDoc = new CourseDoc();
        Long id = course.getId();
        BeanUtils.copyProperties(course, courseDoc);

        courseDoc.setCourseTypeName(courseTypeMapper.selectById(course.getCourseTypeId()).getName());
        // courseDoc.setOnlineTime(new Date());
        BeanUtils.copyProperties(courseDetailMapper.selectById(id), courseDoc);
        BeanUtils.copyProperties(courseMarketMapper.selectById(id), courseDoc);
        return courseDoc;
    }


}
