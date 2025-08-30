package com.mz.remote;

import com.mz.dto.DirectionDrivingResponse;
import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 20:05
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {
    @RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
    public ResponseResult<DirectionDrivingResponse> getDistanceAndDuration(@RequestBody ForecastPriceDto forecastPriceDto);
}
