package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 20:41
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-order")
public interface ServiceOrderClient {
    @RequestMapping(method = RequestMethod.POST,value = "/order/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest);
    /*乘客取消订单*/

    @RequestMapping(method = RequestMethod.POST,value = "/order/cancel")
    public ResponseResult cancel(@RequestParam String orderId,@RequestParam String identity);
}
