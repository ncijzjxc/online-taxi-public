package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.DirectionDrivingResponse;
import com.mz.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 22:28
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
@Slf4j
public class MapDirectionClient {
    @Autowired
    RestTemplate restTemplate;

    @Value("${map.api.key}")
    private String key;
    public DirectionDrivingResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        // 组装url
        StringBuilder urlBuilder=new StringBuilder();
        urlBuilder.append(AmpConfigConstant.DIRECTION_URL);
        urlBuilder.append("?origin=");
        urlBuilder.append(depLongitude+","+depLatitude);
        urlBuilder.append("&destination=");
        urlBuilder.append(destLongitude+","+destLatitude);
        urlBuilder.append("&extensions=all&output=json&key=");
        urlBuilder.append(key);
        log.info("调用url:"+urlBuilder.toString());
        //调用高德地图
        ResponseEntity<String> DirectionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        log.info("高德地图路径规划："+DirectionEntity.getBody());
        //解析接口
        String directionString = DirectionEntity.getBody();
        DirectionDrivingResponse directionDrivingResponse = parseDirectionEntity(directionString);

        return directionDrivingResponse;

    }
    private  DirectionDrivingResponse parseDirectionEntity(String directionString){
        DirectionDrivingResponse directionDrivingResponse=null;
        try {
            JSONObject result = JSONObject.fromObject(directionString);
            if(result.has(AmpConfigConstant.STATUS)){
                int status = result.getInt(AmpConfigConstant.STATUS);
                if(status==1){
                    if(result.has(AmpConfigConstant.ROUTE)){
                        JSONObject router = result.getJSONObject(AmpConfigConstant.ROUTE);
                        JSONArray paths = router.getJSONArray(AmpConfigConstant.PATHS);
                        JSONObject pathObject = paths.getJSONObject(0);
                        directionDrivingResponse=new DirectionDrivingResponse();
                        if(pathObject.has(AmpConfigConstant.DISTANCE)){
                            Integer  distance = pathObject.getInt(AmpConfigConstant.DISTANCE);
                            directionDrivingResponse.setDistance(distance);
                            log.info("获取到的距离"+distance);
                        }

                        if(pathObject.has(AmpConfigConstant.DURATION)){
                            Integer duration = pathObject.getInt(AmpConfigConstant.DURATION);
                            directionDrivingResponse.setDuration(duration);
                            log.info("获取到的时间"+duration);
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return  directionDrivingResponse;
    }
}
