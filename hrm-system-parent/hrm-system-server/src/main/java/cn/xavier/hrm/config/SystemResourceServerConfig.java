package cn.xavier.hrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
//资源服务配置
@Configuration
//开启资源服务配置
@EnableResourceServer
//开启方法授权
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SystemResourceServerConfig extends BasicResourceServerConfig {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/tenant/settlement").permitAll(); // 必须放到super的scope前面，先小后大原则
        super.configure(http);
    }
}
