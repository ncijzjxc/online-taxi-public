package com.mz.remote;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.ResponseResult;
import com.mz.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/8/29 - 08 - 29 - 10:44
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-price")
public interface ServicePriceClient {
    @RequestMapping(method = RequestMethod.GET,value = "/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecast(@RequestBody ForecastPriceDto forecastPriceDto);
}
