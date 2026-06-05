package com.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 部门管理系统启动类
 *
 * @author management
 * @date 2024-01-01
 */
@SpringBootApplication
@MapperScan("com.management.mapper")
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }
}
