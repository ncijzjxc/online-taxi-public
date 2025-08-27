package com.mz.service;

import com.mz.dto.DirectionDrivingResponse;
import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.request.VerifcationCodeDTO;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 19:55
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DirectionService {
    public ResponseResult driving(ForecastPriceDto forecastPriceDto){
        DirectionDrivingResponse directionDrivingResponse=new DirectionDrivingResponse();
        directionDrivingResponse.setDistance(12);
        directionDrivingResponse.setDuration(20);
        return ResponseResult.success(directionDrivingResponse);
    }
}
