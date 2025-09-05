package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.dto.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: mz
 * @Date: 2025/9/3 - 09 - 03 - 15:50
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {
    @RequestMapping(method = RequestMethod.POST,value = "/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name,@RequestParam String desc);
    @RequestMapping(method = RequestMethod.POST,value = "/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid);
}
