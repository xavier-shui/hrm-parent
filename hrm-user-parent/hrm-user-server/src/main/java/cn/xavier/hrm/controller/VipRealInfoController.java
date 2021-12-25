package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IVipRealInfoService;
import cn.xavier.hrm.domain.VipRealInfo;
import cn.xavier.hrm.query.VipRealInfoQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipRealInfo")
public class VipRealInfoController {
    @Autowired
    public IVipRealInfoService vipRealInfoService;


    /**
     * 保存和修改公用的
     * @param vipRealInfo  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody VipRealInfo vipRealInfo){
        try {
            if( vipRealInfo.getId()!=null){
                vipRealInfoService.updateById(vipRealInfo);
            }
            else{
                vipRealInfoService.insert(vipRealInfo);
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
            vipRealInfoService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public VipRealInfo get(@PathVariable("id")Long id)
    {
        return vipRealInfoService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<VipRealInfo> list(){

        return vipRealInfoService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<VipRealInfo> json(@RequestBody VipRealInfoQuery query)
    {
        Page<VipRealInfo> page = new Page<VipRealInfo>(query.getPage(),query.getRows());
        page = vipRealInfoService.selectPage(page);
        return new PageList<VipRealInfo>(page.getTotal(),page.getRecords());
    }
}
