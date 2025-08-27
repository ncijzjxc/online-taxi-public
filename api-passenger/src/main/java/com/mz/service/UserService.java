package com.mz.service;

import com.mz.dto.PassengerUser;
import com.mz.dto.ResponseResult;
import com.mz.dto.TokenResult;
import com.mz.remote.ServicePassengerUserClient;
import com.mz.request.VerifcationCodeDTO;
import com.mz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/26 - 08 - 26 - 13:32
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;
    public ResponseResult getUserByAccessToken(String accessToken){
        //根据token查询手机号
        log.info("获取到的token："+accessToken);
        TokenResult tokenResult = JwtUtil.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("获取到的手机号："+phone);

        VerifcationCodeDTO verifcationCodeDTO=new VerifcationCodeDTO();
        verifcationCodeDTO.setPassengerPhone(phone);
        ResponseResult user = servicePassengerUserClient.getUser(phone);

        return ResponseResult.success(user.getData());
    }
}
