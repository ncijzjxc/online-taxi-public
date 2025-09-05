package com.mz.remote;

import com.mz.dto.Car;
import com.mz.dto.DriverResponse;
import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.request.ApiDriverPointRequest;

import com.mz.response.PointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 19:49
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-driver-user")

public interface ApiDriverClient {
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
    @RequestMapping(method = RequestMethod.GET,value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverResponse> checkDriverUserExists(@PathVariable("driverPhone") String driverPhone);
    @RequestMapping(method = RequestMethod.GET,value = "/car")
    public ResponseResult<Car> getCarById(@RequestParam  Long carId);

}
