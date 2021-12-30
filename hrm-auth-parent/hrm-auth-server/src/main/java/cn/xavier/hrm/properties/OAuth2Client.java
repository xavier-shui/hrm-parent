package cn.xavier.hrm.properties;

import cn.xavier.hrm.constant.LoginUserTypeConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Zheng-Wei Shui
 * @date 12/30/2021
 */
@Data
@Component
@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2Client {
    private OAuth2ClientDetails admin;
    private OAuth2ClientDetails website;

    public OAuth2ClientDetails getclientDetails(Integer type) {
        if ((type != null && type != 0 && type != 1) || type == null) {
            return null;
        }
        return (type.intValue() == LoginUserTypeConstant.ADMIN) ? admin : website;
    }
}
