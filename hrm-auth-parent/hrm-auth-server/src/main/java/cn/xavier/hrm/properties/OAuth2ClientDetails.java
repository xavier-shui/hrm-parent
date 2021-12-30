package cn.xavier.hrm.properties;

import lombok.Data;

// 这个类写到OAuth2Client内部类里面字段赋不到值
@Data
public class OAuth2ClientDetails {
    private String clientId;
    private String clientSecret;
}