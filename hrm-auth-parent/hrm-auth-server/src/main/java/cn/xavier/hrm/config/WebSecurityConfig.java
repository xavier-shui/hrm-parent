package cn.xavier.hrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //密码 编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //授权规则配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()   //屏蔽跨域防护
                .authorizeRequests()          //对请求做授权处理
                .antMatchers("/login", "/loginUser/settlement", "/loginUser/login", "/loginUser/refreshToken", "/meal").permitAll()  //登录路径，入驻路径放行
                .antMatchers("/swagger-resources/**","/webjars/**","/v2/**","/swagger-ui.html/**").permitAll()  // swagger路径放行
                .anyRequest().authenticated() //其他路径都要拦截
                .and().formLogin()  //允许表单登录， 设置登陆页
                .successForwardUrl("/loginSuccess") // 设置登陆成功页（对应//controller），一定要有loginSuccess这个路径
                .and().logout().permitAll();    //登出
    }

    //配置认证管理器，授权模式为“poassword”时会用到
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}