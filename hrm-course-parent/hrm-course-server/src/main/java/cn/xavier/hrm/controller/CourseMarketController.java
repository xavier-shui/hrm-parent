package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.ICourseMarketService;
import cn.xavier.hrm.domain.CourseMarket;
import cn.xavier.hrm.query.CourseMarketQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseMarket")
public class CourseMarketController {
    @Autowired
    public ICourseMarketService courseMarketService;


    /**
     * 保存和修改公用的
     * @param courseMarket  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody CourseMarket courseMarket){
        try {
            if( courseMarket.getId()!=null){
                courseMarketService.updateById(courseMarket);
            }
            else{
                courseMarketService.insert(courseMarket);
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
            courseMarketService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public CourseMarket get(@PathVariable("id")Long id)
    {
        return courseMarketService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<CourseMarket> list(){

        return courseMarketService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<CourseMarket> json(@RequestBody CourseMarketQuery query)
    {
        Page<CourseMarket> page = new Page<CourseMarket>(query.getPage(),query.getRows());
        page = courseMarketService.selectPage(page);
        return new PageList<CourseMarket>(page.getTotal(),page.getRecords());
    }
}
