package com.mz.remote;

import com.mz.dto.Car;
import com.mz.dto.ResponseResult;
import com.mz.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/8 - 09 - 08 - 18:30
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {
    @GetMapping("/checkCityDriver")
    public ResponseResult<Boolean> checkCityDriver(@RequestParam String cityCode);
    @GetMapping ("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);
    @GetMapping("/car")
    public ResponseResult<Car> getCar(@RequestParam Long carId);
}
