package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 20:02
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient(value = "servsion-verificationcode")
public interface ServsionVerificationCodeClient {
    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size")int size);
}
