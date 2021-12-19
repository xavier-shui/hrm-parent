package cn.xavier.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Zheng-Wei Shui
 * @date 12/19/2021
 */
@SpringBootApplication
@EnableEurekaClient
public class OssApp {
    public static void main(String[] args) {
        SpringApplication.run(OssApp.class, args);
    }
}
