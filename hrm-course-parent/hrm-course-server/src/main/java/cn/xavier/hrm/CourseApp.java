package cn.xavier.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Zheng-Wei Shui
 * @date 12/20/2021
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.xavier.hrm.mapper")
@EnableCaching
public class CourseApp {
    public static void main(String[] args) {
        SpringApplication.run(CourseApp.class, args);
    }
}
