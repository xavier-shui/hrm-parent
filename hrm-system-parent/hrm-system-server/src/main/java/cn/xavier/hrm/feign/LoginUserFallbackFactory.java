package cn.xavier.hrm.feign;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.util.AjaxResult;
import feign.hystrix.FallbackFactory;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
public class LoginUserFallbackFactory implements FallbackFactory<ILoginUserFeignClient> {
    @Override
    public ILoginUserFeignClient create(Throwable cause) {
        return new ILoginUserFeignClient() {
            @Override
            public AjaxResult settlement(LoginUser loginUser) {
                return AjaxResult.me().setSuccess(false).setMessage("入驻失败，请稍后重试!");
            }
        };
    }
}
