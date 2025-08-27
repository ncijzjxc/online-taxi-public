package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.DirectionDrivingResponse;
import com.mz.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 22:28
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
@Slf4j
public class MapDirectionClient {
    @Value("987f599c4ae049a884ebc43c472601e5")
    private String key;
    public DirectionDrivingResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        // &extensions=all&output=json&key=987f599c4ae049a884ebc43c472601e5
        StringBuilder urlBuilder=new StringBuilder();
        urlBuilder.append(AmpConfigConstant.urlMap);
        urlBuilder.append("?origin=");
        urlBuilder.append(depLongitude+","+depLatitude);
        urlBuilder.append("&destination=");
        urlBuilder.append(destLongitude+","+destLatitude);
        urlBuilder.append("&extensions=all&output=json&key=");
        urlBuilder.append(key);
        log.info("调用url:"+urlBuilder.toString());
        return null;
    }
}
