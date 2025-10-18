package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/10/19 - 10 - 19 - 19:37
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;
    @PostMapping("/push-pay-info")
    public ResponseResult PayPrice(@RequestParam String orderId,@RequestParam String price,@RequestParam String passengerId ){
        return payService.PayPrice(orderId,price,passengerId);
    }
}
