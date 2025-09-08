package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: mz
 * @Date: 2025/9/8 - 09 - 08 - 19:43
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {
    @RequestMapping(method = RequestMethod.POST,value = "/terminal/aroundSearch")
    public ResponseResult <List<TerminalResponse>> checkCar(@RequestParam String center, @RequestParam Integer radius);
}
