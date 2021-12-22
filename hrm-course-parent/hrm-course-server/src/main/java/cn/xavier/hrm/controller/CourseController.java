package cn.xavier.hrm.controller;

import cn.xavier.hrm.domain.Course;
import cn.xavier.hrm.dto.CourseDto;
import cn.xavier.hrm.query.CourseQuery;
import cn.xavier.hrm.service.ICourseService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    public ICourseService courseService;

    /**
     * 保存和修改公用的
     * @param course  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Course course){
        try {
            if( course.getId()!=null){
                courseService.updateById(course);
            }
            else{
                courseService.insert(course);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }
    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            courseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Course get(@PathVariable("id")Long id)
    {
        return courseService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Course> list(){

        return courseService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<Course> json(@RequestBody CourseQuery query)
    {
        Page<Course> page = new Page<Course>(query.getPage(),query.getRows());
        page = courseService.selectPage(page);
        return new PageList<Course>(page.getTotal(),page.getRecords());
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody CourseDto dto)
    {
        return courseService.save(dto);
    }

    /**
     * 上线课程
     *
     * @param ids ids
     * @return the ajax result
     */
    @PostMapping("/onlineCourse")
    public AjaxResult onlineCourse(@RequestBody List<Long> ids) {
        return courseService.onlineCourse(ids);
    }

    /**
     * 下线课程
     *
     * @param ids ids
     * @return the ajax result
     */
    @PostMapping("/offlineCourse")
    public AjaxResult offlineCourse(@RequestBody List<Long> ids) {
        return courseService.offlineCourse(ids);
    }
}
