package com.mz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 15:04
 * @Description: com.mz
 * @version: 6.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.mz.mapper")
public class ServiceDriverUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDriverUserApplication.class,args);
    }
}
