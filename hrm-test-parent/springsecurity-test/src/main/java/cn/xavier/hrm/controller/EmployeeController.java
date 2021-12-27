package cn.xavier.hrm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @RequestMapping("/employee/list")
    @PreAuthorize("hasAnyAuthority('employee:list')")
    public String list(){
        return "employee.list";
    }
    @RequestMapping("/employee/add")
    @PreAuthorize("hasAnyAuthority('employee:add')")
    public String add(){
        return "employee.add";
    }
    @RequestMapping("/employee/update")
    @PreAuthorize("hasAnyAuthority('employee:update')")
    public String update(){
        return "employee.update";
    }
    @RequestMapping("/employee/delete")
    @PreAuthorize("hasAnyAuthority('employee:delete')")
    public String delete(){
        return "employee.delete";
    }
}