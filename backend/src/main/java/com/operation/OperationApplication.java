package com.operation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan({"com.operation.module.mapper", "com.operation.module.config.mapper"})
public class OperationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperationApplication.class, args);
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     多项目设备安装运维平台 - 启动成功                         ║");
        System.out.println("║     Swagger文档: http://localhost:8080/doc.html              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
