package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import com.mz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 18:21
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        return orderService.addOrder(orderRequest);
    }
}
