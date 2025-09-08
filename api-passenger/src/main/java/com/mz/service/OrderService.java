package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceOrderClient;
import com.mz.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 20:43
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class OrderService {
    @Autowired
    ServiceOrderClient serviceOrderClient;
    public ResponseResult addOrder(OrderRequest orderRequest){
        return serviceOrderClient.addOrder(orderRequest);
    }
}
