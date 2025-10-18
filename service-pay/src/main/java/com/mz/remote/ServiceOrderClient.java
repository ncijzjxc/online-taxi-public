package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: mz
 * @Date: 2025/10/30 - 10 - 30 - 15:03
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-order")
public interface ServiceOrderClient {
    @RequestMapping(method = RequestMethod.POST,value = "/order/pay")
    public ResponseResult<OrderRequest> pay(@RequestParam String orderId) ;

}
