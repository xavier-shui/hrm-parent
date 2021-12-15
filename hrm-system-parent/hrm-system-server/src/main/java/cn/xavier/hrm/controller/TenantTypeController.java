package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.ITenantTypeService;
import cn.xavier.hrm.domain.TenantType;
import cn.xavier.hrm.query.TenantTypeQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenantType")
public class TenantTypeController {
    @Autowired
    public ITenantTypeService tenantTypeService;


    /**
     * 保存和修改公用的
     * @param tenantType  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody TenantType tenantType){
        try {
            if( tenantType.getId()!=null){
                    tenantTypeService.updateById(tenantType);
            }
            else{
                    tenantTypeService.insert(tenantType);
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
            tenantTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public TenantType get(@PathVariable("id")Long id)
    {
        return tenantTypeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<TenantType> list(){

        return tenantTypeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<TenantType> json(@RequestBody TenantTypeQuery query)
    {
        Page<TenantType> page = new Page<TenantType>(query.getPage(),query.getRows());
        page = tenantTypeService.selectPage(page);
        return new PageList<TenantType>(page.getTotal(),page.getRecords());
    }
}
