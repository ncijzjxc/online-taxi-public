package com.mz.controller;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 12:14
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class ForecastPriceController {
    @Autowired
    ForecastPriceService forecastPriceService;
    @PostMapping("/forecast-price")
    public ResponseResult getPrice(@RequestBody ForecastPriceDto forecastPriceDto){
        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();
        return forecastPriceService.getPriceByMap(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
