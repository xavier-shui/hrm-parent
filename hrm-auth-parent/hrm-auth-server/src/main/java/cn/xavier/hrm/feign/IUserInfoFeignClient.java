package cn.xavier.hrm.feign;

import cn.xavier.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
@FeignClient(value = "SYSTEM-SERVER", fallbackFactory = UserInfoFallbackFactory.class)
@RequestMapping("/employee")
public interface IUserInfoFeignClient {

    @GetMapping("/findUserInfoByLoginId/{id}")
    AjaxResult findUserInfoByLoginId(@PathVariable("id") Long id);
}
