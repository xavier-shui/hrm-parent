package cn.xavier.hrm.feign;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
@FeignClient(value = "AUTH-SERVER", fallbackFactory = LoginUserFallbackFactory.class)
@RequestMapping("/loginUser")
public interface ILoginUserFeignClient {

    @PostMapping("/settlement")
    AjaxResult settlement(@RequestBody LoginUser loginUser);
}
