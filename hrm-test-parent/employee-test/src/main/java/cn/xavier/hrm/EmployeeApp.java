package cn.xavier.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zheng-Wei Shui
 * @date 12/15/2021
 */
@SpringBootApplication
@MapperScan("cn.xavier.hrm.mapper")
public class EmployeeApp {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApp.class);
    }
}
