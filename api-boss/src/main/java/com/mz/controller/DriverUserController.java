package com.mz.controller;

import com.mz.dto.*;
import com.mz.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 18:55
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@Slf4j
public class DriverUserController {
    @Autowired
    DriverUserService driverUserService;
    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){

        log.info( JSONObject.fromObject(driverUser).toString());
        return  driverUserService.addDriverUser(driverUser);
    }
    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        log.info(JSONObject.fromObject(driverUser).toString());
        return  driverUserService.updateDriverUser(driverUser);
    }
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car){
        return driverUserService.addCar(car);
    }

    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return  driverUserService.bind(driverCarBindingRelationship);
    }
    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return  driverUserService.unbind(driverCarBindingRelationship);
    }
}
