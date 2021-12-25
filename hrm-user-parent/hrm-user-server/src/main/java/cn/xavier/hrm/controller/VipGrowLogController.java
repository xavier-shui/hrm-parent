package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IVipGrowLogService;
import cn.xavier.hrm.domain.VipGrowLog;
import cn.xavier.hrm.query.VipGrowLogQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipGrowLog")
public class VipGrowLogController {
    @Autowired
    public IVipGrowLogService vipGrowLogService;


    /**
     * 保存和修改公用的
     * @param vipGrowLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody VipGrowLog vipGrowLog){
        try {
            if( vipGrowLog.getId()!=null){
                vipGrowLogService.updateById(vipGrowLog);
            }
            else{
                vipGrowLogService.insert(vipGrowLog);
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
            vipGrowLogService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public VipGrowLog get(@PathVariable("id")Long id)
    {
        return vipGrowLogService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<VipGrowLog> list(){

        return vipGrowLogService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<VipGrowLog> json(@RequestBody VipGrowLogQuery query)
    {
        Page<VipGrowLog> page = new Page<VipGrowLog>(query.getPage(),query.getRows());
        page = vipGrowLogService.selectPage(page);
        return new PageList<VipGrowLog>(page.getTotal(),page.getRecords());
    }
}
