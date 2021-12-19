package cn.xavier.hrm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Zheng-Wei Shui
 * @date 12/19/2021
 */
@Component
@ConfigurationProperties(prefix = "file.alicloud")
@Data
public class AlicloudOSSProperties {
    // 自动将-转为驼峰
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String endpoint;
}
