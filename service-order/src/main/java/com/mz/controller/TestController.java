package com.mz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 16:54
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class TestController {
    @GetMapping("/order/test/{orderId}")
    public String test(@PathVariable("orderId") Integer orderId){
        System.out.println("并发测试打印值:"+orderId);
        return "success"+orderId;
    }
}
