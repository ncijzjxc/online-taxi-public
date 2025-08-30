package com.mz.controller;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.service.ApiDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 19:44
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class ApiDriverController {
    @Autowired
    ApiDriverService apiDriverService;

    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        apiDriverService.updateDriverUser(driverUser);
        return ResponseResult.success();
    }
}
