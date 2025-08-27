package com.mz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mz
 * @Date: 2025/8/20 - 08 - 20 - 23:14
 * @Description: com.mz
 * @version: 6.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApplicationApp {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationApp.class,args);
    }
}
