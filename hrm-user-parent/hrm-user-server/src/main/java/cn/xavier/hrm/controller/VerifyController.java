package cn.xavier.hrm.controller;

import cn.xavier.hrm.dto.SmsCodeDto;
import cn.xavier.hrm.service.IVerifyService;
import cn.xavier.hrm.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
@RestController
@RequestMapping("/verifycode")
public class VerifyController {

    @Autowired
    private IVerifyService verifyService;

    @GetMapping("/imageCode/{key}")
    public AjaxResult imageCode(@PathVariable("key") String key) {
        return verifyService.imageCode(key);
    }

    @PostMapping("/sendSmsCode")
    public AjaxResult sendSmsCode(@RequestBody @Valid SmsCodeDto dto) {
        return verifyService.sendSmsCode(dto);
    }
}
