package cn.xavier.hrm.feign;

import cn.xavier.hrm.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
@Component
public class UserInfoFallbackFactory implements FallbackFactory<IUserInfoFeignClient> {
    @Override
    public IUserInfoFeignClient create(Throwable cause) {
        return new IUserInfoFeignClient() {
            @Override
            public AjaxResult findUserInfoByLoginId(Long id) {
                return AjaxResult.me().setSuccess(false).setMessage("获取用户信息失败!");
            }
        };
    }
}
