package com.mz.Config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: mz
 * @Date: 2025/10/25 - 10 - 25 - 17:15
 * @Description: com.mz
 * @version: 6.0
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayConfig {

    private String appid;
    private String appPrivateKey;
    private String publicKey;

    private String notifyUrl;
    @PostConstruct
    public void init(){


        Config config=new Config();

        //基础配置
        config.protocol="https";
        config.gatewayHost="openapi-sandbox.dl.alipaydev.com";
        config.signType="RSA2";
        //业务配置
        config.appId=this.appid;
        config.merchantPrivateKey=this.appPrivateKey;
        config.alipayPublicKey=this.publicKey;
        config.notifyUrl=this.notifyUrl;

        Factory.setOptions(config);
        System.out.println("初始化完成");

    }
}
