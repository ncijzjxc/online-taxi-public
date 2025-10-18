package com.mz.config;

import org.checkerframework.checker.units.qual.C;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 20:43
 * @Description: com.mz.config
 * @version: 6.0
 */
@Component
public class RedisConfig {
    private String potocol="redis://";
    @Value("${spring.redis.port}")
    private String redisPort;
    @Value("${spring.redis.host}")
    private String redisHost;
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress(potocol+redisHost+":"+redisPort).setDatabase(0);
        return Redisson.create();
    }
}
