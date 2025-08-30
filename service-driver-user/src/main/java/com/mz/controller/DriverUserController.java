package com.mz.controller;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
