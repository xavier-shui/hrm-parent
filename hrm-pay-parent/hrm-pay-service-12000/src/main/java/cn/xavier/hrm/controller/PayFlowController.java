package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IPayFlowService;
import cn.xavier.hrm.domain.PayFlow;
import cn.xavier.hrm.query.PayFlowQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payFlow")
public class PayFlowController {
    @Autowired
    public IPayFlowService payFlowService;


    /**
     * 保存和修改公用的
     * @param payFlow  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody PayFlow payFlow){
        try {
            if( payFlow.getId()!=null)
                payFlowService.updateById(payFlow);
            else
                payFlowService.insert(payFlow);
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
            payFlowService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public PayFlow get(@PathVariable("id")Long id)
    {
        return payFlowService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<PayFlow> list(){

        return payFlowService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<PayFlow> json(@RequestBody PayFlowQuery query)
    {
        Page<PayFlow> page = new Page<PayFlow>(query.getPage(),query.getRows());
        page = payFlowService.selectPage(page);
        return new PageList<PayFlow>(page.getTotal(),page.getRecords());
    }
}
