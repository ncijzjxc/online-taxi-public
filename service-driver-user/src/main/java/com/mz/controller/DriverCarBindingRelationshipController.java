package com.mz.controller;

import com.mz.dto.DriverCarBindingRelationship;
import com.mz.dto.ResponseResult;
import com.mz.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/8/31 - 08 - 31 - 22:27
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {
    @Autowired
    DriverCarBindingRelationshipService driverCarBindingRelationshipService;
    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);

    }
    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);

    }
}
