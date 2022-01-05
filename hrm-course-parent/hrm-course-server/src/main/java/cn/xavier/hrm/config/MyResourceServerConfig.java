/*
package cn.xavier.hrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//资源服务配置
@Configuration
//开启资源服务配置
@EnableResourceServer
//开启方法授权
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
    //1.资源服务安全配置

    //1.1资源服务令牌验证服务,通过远程校验令牌
    @Bean
    public ResourceServerTokenServices resourceServerTokenServices(){
        //使用远程服务请求授权服务器校验token ， 即：资源服务和授权服务器不在一个主机
        RemoteTokenServices services = new RemoteTokenServices();
        //授权服务地址 , 当浏览器访问某个资源时就会调用该远程授权服务地址去校验token
        //要求请求中必须携带token
        services.setCheckTokenEndpointUrl("http://47.108.183.122:11004/oauth/check_token");
        //客户端id，对应认证服务的客户端详情配置的clientId
        services.setClientId("admin");
        //密钥，对应认证服务的客户端详情配置的clientId
        services.setClientSecret("1");
        return services;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
       */
/* //我的资源名称是什么
        resources.resourceId("courseId");
        //用来校验，解析Token的服务
        resources.tokenServices(resourceServerTokenServices());*//*

        //资源ID
        resources.resourceId("courseId")
                .tokenStore(tokenStore())
                //验证令牌的服务，令牌验证通过才允许获取资源
                //.tokenServices(resourceServerTokenServices())
                //无状态
                .stateless(true);

    }

    //修改JWT令牌
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
    private final String sign_key  = "1";

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
                //校验scope必须为hrm ， 对应认证服务的客户端详情配置的clientId
                .antMatchers("/**").access("#oauth2.hasScope('hrm')")
                .antMatchers("/swagger-resources/**","/webjars/**","/v2/**","/swagger-ui.html/**").permitAll()  // swagger路径放行
                //关闭跨域伪造检查
                .and().csrf().disable()
                //把session设置为无状态，意思是使用了token，那么session不再做数据的记录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}*/
