package cn.xavier.hrm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

//授权服务配置
@Configuration
//开启授权服务配置
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 定义客户端详情服务，基于数据库,自动加载oauth_client_details表中的数据
    @Bean
    public ClientDetailsService clientDetailsService(){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        //数据库的secret秘钥是密文
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    // 授权码的管理服务 ,默认读取 oauth_code表
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // 令牌的管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        //创建默认的令牌服务
        DefaultTokenServices services = new DefaultTokenServices();
        //指定客户端详情配置
        services.setClientDetailsService(clientDetailsService());
        //支持产生刷新token
        services.setSupportRefreshToken(true);
        //token存储方式
        services.setTokenStore(tokenStore());
        return services;
    }

    //基于内存的Token存储
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //1.密码授权模式需要
                .authenticationManager(authenticationManager)
                //2.授权码模式服务
                // TODO Invalid authorization code
                .authorizationCodeServices(authorizationCodeServices())
                //3.配置令牌管理服务
                .tokenServices(tokenService())
                //允许post方式请求
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    // 授权服务安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //对应/oauth/check_token ，路径公开
                .checkTokenAccess("permitAll()")
                //允许客户端进行表单身份验证,使用表单认证申请令牌
                .allowFormAuthenticationForClients();
    }



}
