package cn.xavier.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
@MapperScan("cn.xavier.hrm.mapper")
//@EnableFeignClients
public class PayServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceApp.class);
    }
}
