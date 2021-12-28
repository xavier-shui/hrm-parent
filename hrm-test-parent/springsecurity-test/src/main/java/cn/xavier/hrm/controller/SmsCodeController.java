package cn.xavier.hrm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class SmsCodeController {

    @RequestMapping(value = "/sms/send/{phone}",method = RequestMethod.POST)
    public void sendSms(@PathVariable("phone")String phone, HttpServletRequest request){
        String code = UUID.randomUUID().toString().substring(0,4);
        request.getSession().setAttribute("SMS_CODE_IN_SESSION", code);
        System.out.println("手机验证码为："+code);
    }
}