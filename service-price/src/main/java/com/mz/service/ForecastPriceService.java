package com.mz.service;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.remote.MapDirectionClient;
import com.mz.remote.ServiceMapClient;
import com.mz.response.ForecastPriceResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ServiceMapClient serviceMapClient;
    @Autowired
    MapDirectionClient mapDirectionClient;

    public ResponseResult getPriceByMap( ForecastPriceDto forecastPriceDto){
       /* log.info("出发地经度："+forecastPriceDto.getDepLongitude());
        log.info("出发地维度："+ forecastPriceDto.getDepLatitude());
        log.info("目的地经度："+forecastPriceDto.getDestLongitude());
        log.info("目的地经度："+forecastPriceDto.getDestLatitude());*/
        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();


        log.info("调用地图服务：");
        //ResponseResult price = serviceMapClient.getPrice(forecastPriceDto);

        log.info("查询地图服务");
        mapDirectionClient.direction(depLongitude,depLatitude,destLongitude,destLatitude);

        log.info("计算计价规则 ");
        ForecastPriceResponse forecastPriceResponse=new ForecastPriceResponse();
        forecastPriceResponse.setPrice(21.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
