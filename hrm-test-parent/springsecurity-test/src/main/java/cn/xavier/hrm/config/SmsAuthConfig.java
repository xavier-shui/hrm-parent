package cn.xavier.hrm.config;

import cn.xavier.hrm.authenticationhandler.MyAuthenticationFailureHandler;
import cn.xavier.hrm.authenticationhandler.MyAuthenticationSuccessHandler;
import cn.xavier.hrm.filter.SmsCodeAuthenticationProvider;
import cn.xavier.hrm.filter.SmsLoginFilter;
import cn.xavier.hrm.userdetail.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//security 的配置
@Configuration
@EnableWebSecurity  //开启Security
public class SmsAuthConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Override
    public  void configure(HttpSecurity http) throws Exception {

        //短信认证Filter
        SmsLoginFilter smsLoginFilter = new SmsLoginFilter();
        smsLoginFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsLoginFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        smsLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //短信认证provider
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(loginUserDetailsService); // 自动注入不行，因为new的


        //添加短信认证provider
        http.authenticationProvider(smsCodeAuthenticationProvider);
        //添加短信认证filter
        http.addFilterAfter(smsLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}