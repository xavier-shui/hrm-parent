package cn.xavier.hrm.config;

import cn.xavier.hrm.authenticationhandler.MyAuthenticationFailureHandler;
import cn.xavier.hrm.authenticationhandler.MyAuthenticationSuccessHandler;
import cn.xavier.hrm.authorizationhandler.MyAccessDeniedHandler;
import cn.xavier.hrm.authorizationhandler.MyAuthenticationEntryPoint;
import cn.xavier.hrm.filter.SmsCodeCheckFilter;
import cn.xavier.hrm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author Zheng-Wei Shui
 * @date 12/26/2021
 */
// @Configuration
@EnableWebSecurity // 含 @Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法安全
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsAuthConfig smsAuthConfig;

/*
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        User user = new User("root", "123", new ArrayList<>());
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager(user);
        return detailsManager;
    }
*/

    @Bean
    public PasswordEncoder get() {
        // return NoOpPasswordEncoder.getInstance();
        // hash 结果每次不一样，
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = http.authorizeRequests();
        // 查全部
       /* List<Permission> permissions = permissionMapper.selectList(null);
        permissions.stream().forEach(permission -> {
            // 资源需要相应的权限
            expressionInterceptUrlRegistry.antMatchers(permission.getResource()).hasAuthority(permission.getSn());
        });*/
        expressionInterceptUrlRegistry //授权配置
                .antMatchers("/login", "/sms/send/*", "/smsLogin").permitAll()  //登录路径放行
                .antMatchers("/login.html").permitAll() //对登录页面跳转路径放行(配置自己的登录页面需要)
                .anyRequest().authenticated()  //其他路径都要认证
                .and().formLogin()  //允许表单登录
                // .successForwardUrl("/test/resource")  // 设置登陆成功页
                .successHandler(successHandler) // 认证成功处理
                .failureHandler(failureHandler) // 认证失败处理
                .loginPage("/login.html")   //登录页面跳转地址(配置自己的登录页面需要)
                .loginProcessingUrl("/login")   //登录处理地址(必须)(配置自己的登录页面需要)
                .and().logout().permitAll() //登出路径放行 /logout， 默认的
                .and().csrf().disable();  //关闭跨域伪造检查
        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint()); //身份认证验证失败配置

        http.rememberMe()
                .tokenRepository(persistentTokenRepository())	//持久
                .tokenValiditySeconds(3600)	//过期时间
                .userDetailsService(userDetailsService); //用来加载用户认证信息的

        //验证码检查的filter
        SmsCodeCheckFilter smsCodeCheckFilter = new SmsCodeCheckFilter();
        smsCodeCheckFilter.setMyAuthenticationFailureHandler(failureHandler);
        //添加验证码检查的filter
        http.addFilterBefore(smsCodeCheckFilter, UsernamePasswordAuthenticationFilter.class);

        // 验证码登录的
        http.apply(smsAuthConfig);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl obj = new JdbcTokenRepositoryImpl();
        obj.setDataSource(dataSource);
        //启动创建表persistent_logs表，存token，username时会用到
        // obj.setCreateTableOnStartup(true);
        return obj;
    }

}
