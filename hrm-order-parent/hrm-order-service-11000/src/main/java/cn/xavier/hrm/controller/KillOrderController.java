package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IKillOrderService;
import cn.xavier.hrm.domain.KillOrder;
import cn.xavier.hrm.query.KillOrderQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/killOrder")
public class KillOrderController {
    @Autowired
    public IKillOrderService killOrderService;


    /**
     * 保存和修改公用的
     * @param killOrder  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody KillOrder killOrder){
        try {
            if( killOrder.getId()!=null)
                killOrderService.updateById(killOrder);
            else
                killOrderService.insert(killOrder);
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
            killOrderService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public KillOrder get(@PathVariable("id")Long id)
    {
        return killOrderService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<KillOrder> list(){

        return killOrderService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<KillOrder> json(@RequestBody KillOrderQuery query)
    {
        Page<KillOrder> page = new Page<KillOrder>(query.getPage(),query.getRows());
        page = killOrderService.selectPage(page);
        return new PageList<KillOrder>(page.getTotal(),page.getRecords());
    }
}
