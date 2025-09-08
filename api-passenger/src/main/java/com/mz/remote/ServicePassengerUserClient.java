package com.mz.remote;

import com.mz.dto.ResponseResult;
import com.mz.request.VerifcationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;

/**
 * @Auther: mz
 * @Date: 2025/8/23 - 08 - 23 - 23:29
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {
    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerifcationCodeDTO verifcationCodeDTO);


    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone")String phone);
}
