package com.mz.controller;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.response.ForecastPriceResponse;
import com.mz.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 19:41
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/direction")
public class DirectionController {
    @Autowired
    DirectionService directionService;
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody  ForecastPriceDto forecastPriceDto){
        return  directionService.driving(forecastPriceDto);
    }
}
