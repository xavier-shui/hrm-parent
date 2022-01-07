package cn.xavier.hrm.controller;

import cn.xavier.hrm.dto.KillDto;
import cn.xavier.hrm.service.IKillCourseService;
import cn.xavier.hrm.domain.KillCourse;
import cn.xavier.hrm.query.KillCourseQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/killCourse")
public class KillCourseController {
    @Autowired
    public IKillCourseService killCourseService;

    /**
     * 秒杀课程的ID
     * 随机码
     */
    @PutMapping("/kill")
    public AjaxResult kill(@RequestBody KillDto  killDto){
        return killCourseService.kill(killDto);
    }
    //查询所有的发布的课程
    @GetMapping("/publishs")
    public AjaxResult publishs(){
        return killCourseService.publishs();
    }
    //查询某一个发布的课程
    @GetMapping("/publishs/{id}")
    public AjaxResult publishsOne(@PathVariable("id")Long id){
        return killCourseService.publishsOne(id);
    }

    /**
     * 保存和修改公用的
     * @param killCourse  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping("/add")
    public AjaxResult add(@RequestBody KillCourse killCourse){
        return killCourseService.add(killCourse);
    }

    /**
     * 保存和修改公用的
     * @param killCourse  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody KillCourse killCourse){
        try {
            if( killCourse.getId()!=null)
                killCourseService.updateById(killCourse);
            else
                killCourseService.insert(killCourse);
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
            killCourseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public KillCourse get(@PathVariable("id")Long id)
    {
        return killCourseService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<KillCourse> list(){

        return killCourseService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<KillCourse> json(@RequestBody KillCourseQuery query)
    {
        Page<KillCourse> page = new Page<KillCourse>(query.getPage(),query.getRows());
        page = killCourseService.selectPage(page);
        return new PageList<KillCourse>(page.getTotal(),page.getRecords());
    }
}
