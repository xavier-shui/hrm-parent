package cn.xavier.hrm.controller;

import cn.xavier.hrm.util.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping("/loginSuccess")
    public AjaxResult loginSuccess() {
        return AjaxResult.me().setMessage("登錄成功");
    }
}