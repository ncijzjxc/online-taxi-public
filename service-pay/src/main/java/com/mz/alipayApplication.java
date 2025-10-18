package com.mz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: mz
 * @Date: 2025/10/19 - 10 - 19 - 23:11
 * @Description: com.mz
 * @version: 6.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class alipayApplication {
    public static void main(String[] args) {
        SpringApplication.run(alipayApplication.class,args);
    }
}
