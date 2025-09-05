package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.response.PointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 20:59
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {
    @RequestMapping(method = RequestMethod.POST,value = "point/upload")
    public ResponseResult upload(@RequestBody PointResponse pointResponse);
}
