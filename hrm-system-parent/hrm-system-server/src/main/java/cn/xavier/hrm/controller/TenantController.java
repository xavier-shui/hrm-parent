package cn.xavier.hrm.controller;

import cn.xavier.hrm.domain.Tenant;
import cn.xavier.hrm.dto.SettlementDto;
import cn.xavier.hrm.query.TenantQuery;
import cn.xavier.hrm.service.ITenantService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    public ITenantService tenantService;

    /**
     * 机构入驻
     *
     * @return the
     */
    @PostMapping("/settlement")
    public AjaxResult settlement(@RequestBody SettlementDto dto) {
        return tenantService.settlement(dto);
    }


    /**
     * 保存和修改公用的
     * @param tenant  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Tenant tenant){
        try {
            if( tenant.getId()!=null){
                tenantService.updateById(tenant);
            }
            else{
                tenantService.insert(tenant);
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
            tenantService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Tenant get(@PathVariable("id")Long id)
    {
        return tenantService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Tenant> list(){

        return tenantService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<Tenant> json(@RequestBody TenantQuery query)
    {
        Page<Tenant> page = new Page<Tenant>(query.getPage(),query.getRows());
        // page = tenantService.selectPage(page);
        page = tenantService.queryTenantList(page, query);
        return new PageList<Tenant>(page.getTotal(),page.getRecords());
    }
}
