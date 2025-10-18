package com.mz.controller;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.PriceDto;
import com.mz.dto.ResponseResult;
import com.mz.service.PriceService;
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
public class PriceController {
    @Autowired
    PriceService priceService;
    @PostMapping("/forecast-price")
    public ResponseResult getPrice(@RequestBody ForecastPriceDto forecastPriceDto){
        return priceService.getPriceByMap(forecastPriceDto);
    }
    @PostMapping("/calculate-price")
    public ResponseResult actualPrice(@RequestBody PriceDto priceDto){
        return priceService.getCalculatePrice(priceDto);
    }
}
