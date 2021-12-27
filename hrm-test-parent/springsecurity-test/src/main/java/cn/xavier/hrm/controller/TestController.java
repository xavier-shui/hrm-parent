package cn.xavier.hrm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng-Wei Shui
 * @date 12/26/2021
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/resource")
    public String resource() {
        return "Here you are!";
    }
}
