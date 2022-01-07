package cn.xavier.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.xavier.hrm.mapper")

public class KillServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(KillServiceApp.class);
    }
}
