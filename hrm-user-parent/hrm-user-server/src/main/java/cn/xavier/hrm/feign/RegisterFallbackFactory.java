package cn.xavier.hrm.feign;

import cn.xavier.hrm.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
@Component
public class RegisterFallbackFactory implements FallbackFactory<IRegisterFeignClient> {
    @Override
    public IRegisterFeignClient create(Throwable cause) { return loginUser -> AjaxResult.me().setSuccess(false).setMessage("注册失败");}
}
