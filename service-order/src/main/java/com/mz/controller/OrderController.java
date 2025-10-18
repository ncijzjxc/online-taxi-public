package com.mz.controller;

import com.mz.dto.PassengerUser;
import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import com.mz.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /*添加订单*/
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest){
        log.info("订单地址:"+orderRequest.getAddress());
        return orderService.addOrder(orderRequest);
    }
    /*司机去接乘客*/
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult ToPickUpPassenger(@RequestBody OrderRequest orderRequest){
        return orderService.ToPickUpPassenger(orderRequest);
    }
    /*司机到达乘客上车点*/
    @PostMapping("/arrived-departure")
    public ResponseResult ArrivedDeparture(@RequestBody OrderRequest orderRequest){
        return orderService.ArrivedDeparture(orderRequest);
    }
    /*司机接到乘客*/
    @PostMapping("/pick-up-passenger")
    public ResponseResult PickUpPassenger(@RequestBody OrderRequest orderRequest){
        return orderService.PickUpPassenger(orderRequest);
    }
    /*乘客下车*/
    @PostMapping("/passenger-getoff")
    public ResponseResult PassengerGetOff(@RequestBody OrderRequest orderRequest){
        return orderService.PassengerGetOff(orderRequest);
    }

}
