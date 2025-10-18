package com.mz.remote;
import com.mz.dto.ResponseResult;
import com.mz.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: mz
 * @Date: 2025/10/18 - 10 - 18 - 15:38
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-order")
public interface ServiceOrderClient {
    @RequestMapping(method = RequestMethod.POST,value = "/order/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/order/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest);
    @RequestMapping(method = RequestMethod.POST,value = "/order/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);
    @RequestMapping(method = RequestMethod.POST,value = "/order/passenger-getoff")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/order/cancel")
    public ResponseResult cancelOrder(@RequestParam String  orderId,@RequestParam String identity);
}

