package com.mz.remote;

import com.mz.dto.DirectionDrivingResponse;
import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.util.RedisPrefixUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 19:49
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-driver-user")

public interface ApiDriver {
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
}
