package com.mz.remote;

import com.mz.dto.DriverResponse;
import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/9/1 - 09 - 01 - 15:16
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("servsion-verificationcode")
public interface ServisonVerificationCodeClient {
    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> verificationCode(@PathVariable("size") int size);
}
