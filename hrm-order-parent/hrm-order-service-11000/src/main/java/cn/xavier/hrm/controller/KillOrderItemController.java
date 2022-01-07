package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IKillOrderItemService;
import cn.xavier.hrm.domain.KillOrderItem;
import cn.xavier.hrm.query.KillOrderItemQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/killOrderItem")
public class KillOrderItemController {
    @Autowired
    public IKillOrderItemService killOrderItemService;


    /**
     * 保存和修改公用的
     * @param killOrderItem  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody KillOrderItem killOrderItem){
        try {
            if( killOrderItem.getId()!=null)
                killOrderItemService.updateById(killOrderItem);
            else
                killOrderItemService.insert(killOrderItem);
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
            killOrderItemService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public KillOrderItem get(@PathVariable("id")Long id)
    {
        return killOrderItemService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<KillOrderItem> list(){

        return killOrderItemService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<KillOrderItem> json(@RequestBody KillOrderItemQuery query)
    {
        Page<KillOrderItem> page = new Page<KillOrderItem>(query.getPage(),query.getRows());
        page = killOrderItemService.selectPage(page);
        return new PageList<KillOrderItem>(page.getTotal(),page.getRecords());
    }
}
