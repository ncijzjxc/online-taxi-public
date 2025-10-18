package com.mz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 22:54
 * @Description: com.mz
 * @version: 6.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SseDriverClientWeb {
    public static void main(String[] args) {
        SpringApplication.run(SseDriverClientWeb.class,args);
    }
}
