package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceOrderClient;
import com.mz.request.OrderRequest;
import com.mz.service.ApiDriverOrderService;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/10/18 - 10 - 18 - 15:36
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/order")
public class ApiDriverOrderController {
    @Autowired
    ApiDriverOrderService apiDriverOrderService;
    /*去接乘客*/
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult ToPickUpPassenger(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderService.toPickUpPassenger(orderRequest);
    }

    /*司机到达乘客上车点*/
    @PostMapping("/arrived-departure")
    public ResponseResult ArrivedDeparture(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderService.arrivedDeparture(orderRequest);
    }

    /*司机接到乘客*/
    @PostMapping("/pick-up-passenger")
    public ResponseResult PickUpPassenger(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderService.pickUpPassenger(orderRequest);
    }
    /*乘客下车*/
    @PostMapping("/passenger-getoff")
    public ResponseResult PassengerGetOff(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderService.passengerGetOff(orderRequest);
    }
    /*司机端订单取消*/
    @PostMapping("/cancel")
    public ResponseResult cancelOrder(@RequestParam String orderId, @RequestParam String identity){
        return apiDriverOrderService.cancel(orderId,identity);
    }
}
