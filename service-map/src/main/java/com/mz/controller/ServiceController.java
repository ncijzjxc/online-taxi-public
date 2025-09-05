package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 18:50
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    ServiceFromMapService serviceFromMapService;
    @PostMapping("/add")
    public ResponseResult addService(String name){
        return serviceFromMapService.addService(name);
    }
}
