package cn.xavier.hrm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @RequestMapping("/dept/list")
    @PreAuthorize("hasAnyAuthority('dept:list')")
    public String list(){ return "dept.list"; }

    @RequestMapping("/dept/add")
    @PreAuthorize("hasAnyAuthority('dept:add')")
    public String add(){
        return "dept.add";
    }

    @RequestMapping("/dept/update")
    @PreAuthorize("hasAnyAuthority('dept:update')")
    public String update(){
        return "dept.update";
    }

    @RequestMapping("/dept/delete")
    @PreAuthorize("hasAnyAuthority('dept:delete')")
    public String delete(){
        return "dept.delete";
    }

}