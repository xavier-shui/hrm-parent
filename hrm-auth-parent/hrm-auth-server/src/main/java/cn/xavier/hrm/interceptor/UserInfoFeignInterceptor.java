package cn.xavier.hrm.interceptor;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
@Component
public class UserInfoFeignInterceptor implements RequestInterceptor {
    private static final String url = "http://47.108.183.122:11002/hrm/auth/oauth/token?client_id=system&client_secret=1&grant_type=client_credentials&redirect_uri=http://www.zhihu.com";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String result = HttpUtil.get(url);
        Map<String, String> map = JSON.parseObject(result, Map.class);
        requestTemplate.header("Authorization", "bearer " + map.get("access_token"));
    }
}
