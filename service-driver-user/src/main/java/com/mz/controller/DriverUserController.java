package com.mz.controller;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.response.OrderDriverResponse;
import com.mz.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 16:19
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class DriverUserController {
    @Autowired
    DriverUserService driverUserService;
    @PostMapping("/user")
    public ResponseResult insertDriverUser(@RequestBody DriverUser driverUser){
        return  driverUserService.insertDriverUser(driverUser);
    }
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult isExists(@PathVariable("driverPhone")String driverPhone){

        return driverUserService.getDriverUserByPhone(driverPhone);
    }
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse>  availableDriver(@PathVariable("carId") Long carId){
        return driverUserService.availableDriver(carId);
    }

}
