package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.service.CityDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/8 - 09 - 08 - 17:51
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class CityDriverController {
    @Autowired
    CityDriverService cityDriverService;
    @GetMapping ("/checkCityDriver")
    public ResponseResult<Boolean> checkCityDriver( String cityCode){
        return cityDriverService.checkCityDriver(cityCode);
    }
}
