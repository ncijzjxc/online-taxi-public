package com.mz.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 18:26
 * @Description: com.mz.interceptor
 * @version: 6.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check")
                .excludePathPatterns("/token-refresh");



    }
}
