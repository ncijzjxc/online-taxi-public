package com.mz.remote;

import com.mz.dto.ForecastPriceDto;
import com.mz.dto.OrderInfo;
import com.mz.dto.ResponseResult;
import com.mz.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/9/7 - 09 - 07 - 19:59
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-price")
public interface ServicePriceClient {
    @RequestMapping(method = RequestMethod.POST,value = "/forecast-price")
    public ResponseResult<ForecastPriceResponse> checkPrice(@RequestBody ForecastPriceDto forecastPriceDto);
}
