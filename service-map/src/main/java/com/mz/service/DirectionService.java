package com.mz.service;

import com.mz.dto.DirectionDrivingResponse;
import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.remote.MapDirectionClient;
import com.mz.request.VerifcationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 19:55
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DirectionService {
    @Autowired
    MapDirectionClient mapDirectionClient;
    public ResponseResult driving(ForecastPriceDto forecastPriceDto){
        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();
        return ResponseResult.success(mapDirectionClient.direction(depLongitude,depLatitude,destLongitude,destLatitude));
    }
}
