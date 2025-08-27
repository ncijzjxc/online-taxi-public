package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 12:32
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
@Slf4j
public class ForecastPriceService {
    public ResponseResult getPriceByMap(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        log.info("出发地经度："+depLongitude);
        log.info("出发地维度："+ depLatitude);
        log.info("目的地经度："+destLongitude);
        log.info("目的地经度："+destLatitude);

        log.info("调用计价服务：");
        ForecastPriceResponse forecastPriceResponse=new ForecastPriceResponse();
        forecastPriceResponse.setPrice(21.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
