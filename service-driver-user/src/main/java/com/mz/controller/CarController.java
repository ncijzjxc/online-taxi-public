package com.mz.controller;

import com.mz.dto.Car;
import com.mz.dto.ResponseResult;
import com.mz.request.ApiDriverPointRequest;
import com.mz.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 23:57
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class CarController {
    @Autowired
    CarService carService;
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car){
        return carService.addCar(car);
    }
    @GetMapping("/car")
    public ResponseResult getCarById(Long carId){
        return  carService.getCarById(carId);
    }
}
