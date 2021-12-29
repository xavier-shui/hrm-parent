package cn.xavier.hrm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//资源服务配置
@Configuration
//开启资源服务配置
@EnableResourceServer
//开启方法授权
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicResourceServerConfig extends ResourceServerConfigurerAdapter {
    //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
    private final String sign_key  = "1";

    @Value("${oauth2.resourceId}")
    private String resourceId;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId)
                .tokenStore(tokenStore())
                .stateless(true); //无状态

    }
    //修改JWT令牌
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //JWT令牌校验工具
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
        jwtAccessTokenConverter.setSigningKey(sign_key);
        return jwtAccessTokenConverter;
    }

    //2.资源服务的资源的授权配置，比如那些资源放行，那些资源需要什么权限等等
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //校验scope必须为all ， 对应认证服务的客户端详情配置的clientId
                .antMatchers("/swagger-resources/**","/webjars/**","/v2/**","/swagger-ui.html/**").permitAll()  // swagger路径放行， 必须放到scope前面
                .antMatchers("/**").access("#oauth2.hasScope('hrm')")
                //关闭跨域伪造检查
                .and().csrf().disable()
                //把session设置为无状态，意思是使用了token，那么session不再做数据的记录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}