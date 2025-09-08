package com.mz.remote;

import com.mz.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: mz
 * @Date: 2025/9/8 - 09 - 08 - 18:30
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {
    @GetMapping("/checkCityDriver")
    public ResponseResult<Boolean> checkCityDriver(@RequestParam String cityCode);
}
