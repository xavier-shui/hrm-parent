package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.ISystemDictionaryService;
import cn.xavier.hrm.domain.SystemDictionary;
import cn.xavier.hrm.query.SystemDictionaryQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {
    @Autowired
    public ISystemDictionaryService systemDictionaryService;


    /**
     * 保存和修改公用的
     * @param systemDictionary  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody SystemDictionary systemDictionary){
        try {
            if( systemDictionary.getId()!=null){
                systemDictionaryService.updateById(systemDictionary);
            }
            else{
                systemDictionaryService.insert(systemDictionary);
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
            systemDictionaryService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public SystemDictionary get(@PathVariable("id")Long id)
    {
        return systemDictionaryService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<SystemDictionary> list(){

        return systemDictionaryService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<SystemDictionary> json(@RequestBody SystemDictionaryQuery query)
    {
        Page<SystemDictionary> page = new Page<SystemDictionary>(query.getPage(),query.getRows());
        page = systemDictionaryService.selectPage(page);
        return new PageList<SystemDictionary>(page.getTotal(),page.getRecords());
    }

    /**
     * 查看课程等级
     * @return
     */
    @GetMapping("/courseLevel/{type}")
    public AjaxResult getCourseLevel(@PathVariable("type") String type){
        return systemDictionaryService.getCourseLevel(type);
    }
}
