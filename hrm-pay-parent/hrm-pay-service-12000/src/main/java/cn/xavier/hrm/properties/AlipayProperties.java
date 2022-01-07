package cn.xavier.hrm.properties;

import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alibabapay")
@Data
public class AlipayProperties {
    private String appId  ;
    private String protocol  ;
    private String gatewayHost  ;
    private String alibabaPublicKey ;
    private String merchantPrivateKey ;
    private String signType  ;
    private String notifyUrl  ;
    private String returnUrl  ;

    public Config getOptions() {
        Config config = new Config();
        config.protocol = protocol;
        config.gatewayHost = gatewayHost;
        config.signType = signType;
        config.appId = appId;
        //应用私钥
        config.merchantPrivateKey = merchantPrivateKey;
        //支付宝公钥
        config.alipayPublicKey = alibabaPublicKey;
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyUrl;
        return config;
    }
}