package cn.xavier.hrm.controller;

import cn.xavier.hrm.service.IEmployeeService;
import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.query.EmployeeQuery;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    public IEmployeeService employeeService;


    /**
     * 保存和修改公用的
     * @param employee  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Employee employee){
        try {
            if( employee.getId()!=null)
                employeeService.updateById(employee);
            else
                employeeService.insert(employee);
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
            employeeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Employee get(@PathVariable("id")Long id)
    {
        return employeeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Employee> list(){

        return employeeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<Employee> json(@RequestBody EmployeeQuery query)
    {
        Page<Employee> page = new Page<Employee>(query.getPage(),query.getRows());
        // page = employeeService.selectPage(page);
        EntityWrapper<Employee> wrapper = new EntityWrapper<>();
        // 链式编程 查询条件构造， 测试数据
        wrapper.like("username", "baima")
                .and()
                .eq("email", "66.com");
        employeeService.selectMapsPage(page, wrapper);
        return new PageList<Employee>(page.getTotal(),page.getRecords());
    }
}
