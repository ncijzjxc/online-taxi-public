package com.mz.service;

import com.mz.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/10/30 - 10 - 30 - 15:01
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class AlipayService {
    @Autowired
    ServiceOrderClient serviceOrderClient;


    public void alipay(String orderId){
        serviceOrderClient.pay(orderId);
    }
}
