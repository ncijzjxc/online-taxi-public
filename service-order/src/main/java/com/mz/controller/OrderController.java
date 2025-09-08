package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import com.mz.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 20:39
 * @Description: com.mz
 * @version: 6.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest){
        log.info("订单地址:"+orderRequest.getAddress());
        return orderService.addOrder(orderRequest);
    }
}
