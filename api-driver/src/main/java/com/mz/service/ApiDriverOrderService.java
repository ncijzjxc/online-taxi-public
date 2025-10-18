package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceOrderClient;
import com.mz.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: mz
 * @Date: 2025/10/18 - 10 - 18 - 15:37
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class ApiDriverOrderService {
    @Autowired
    ServiceOrderClient serviceOrderClient;
    /*司机去接乘客*/
    public ResponseResult toPickUpPassenger( OrderRequest orderRequest){
        return serviceOrderClient.toPickUpPassenger(orderRequest);
    }
    /*司机到达乘客上车点*/
    public ResponseResult arrivedDeparture(OrderRequest orderRequest){
        return serviceOrderClient.arrivedDeparture(orderRequest);
    }
    public ResponseResult pickUpPassenger(OrderRequest orderRequest){
        return serviceOrderClient.pickUpPassenger(orderRequest);
    }

    public ResponseResult passengerGetOff(OrderRequest orderRequest){
        return serviceOrderClient.passengerGetOff(orderRequest);
    }
    /*订单取消*/
    public ResponseResult cancel(String orderId, String identity) {
        return serviceOrderClient.cancelOrder(orderId,identity);
    }
}
