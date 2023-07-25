package com.xinxu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;

@DependsOn("flywayInitializer")
@SpringBootApplication(scanBasePackages = "com.xinxu.user")
@MapperScan(basePackages = "com.xinxu.user.mapper")
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
