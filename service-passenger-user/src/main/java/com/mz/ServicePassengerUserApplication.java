package com.mz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: mz
 * @Date: 2025/8/22 - 08 - 22 - 13:18
 * @Description: com.mz
 * @version: 6.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mz.mapper")
public class ServicePassengerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerUserApplication.class,args);
    }
}
