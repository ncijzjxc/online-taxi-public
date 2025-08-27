package com.mz.service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.IdentityConstant;
import com.mz.constant.TokenTypeConstant;
import com.mz.dto.ResponseResult;
import com.mz.remote.ServicePassengerUserClient;
import com.mz.remote.ServsionVerificationCodeClient;
import com.mz.request.VerifcationCodeDTO;
import com.mz.response.NumberCodeResponse;
import com.mz.response.TokenResponse;
import com.mz.util.JwtUtil;
import com.mz.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 12:50
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class VerifcationCodeService {
    @Autowired
    ServsionVerificationCodeClient servsionVerificationCodeClient;
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /*
    * 生成验证码
    * */
    public  ResponseResult generatorCode(String passengerPhone){
       //调用验证码
        System.out.println("乘客的手机号是："+passengerPhone);
        ResponseResult<NumberCodeResponse> numberCodeResponse = servsionVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("生成的验证码是："+numberCode);
        String key= RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        //存入redis
        System.out.println("存入redis");
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2,TimeUnit.MINUTES);
        System.out.println("保存到Redis: key=" +key + ", value=" + numberCode);
        //返回值
        return ResponseResult.success("");
    }


    /*
    * 检查验证码
    * */
    public ResponseResult checkCode(String passengerPhone,String code){
        //从redis中获取验证码
        System.out.println("从redis中获取验证码");
        //生成key
        String key =RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        //根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("从Redis中获取: key=" +key + ", value=" + codeRedis);
        System.out.println("redis:"+codeRedis);
        //进行效验
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }
        if(!code.trim().equals(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }
        System.out.println("进行效验");
        //效验成功，判断用户是否有数据，没有则插入，有则放行
        VerifcationCodeDTO verifcationCodeDTO = new VerifcationCodeDTO();
        verifcationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verifcationCodeDTO);
        //生成令牌
        String accessToken = JwtUtil.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenTypeConstant.accessToken);
        String refreshToken=JwtUtil.generatorToken(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenTypeConstant.refreshToken);
        //存入redis中
        String accessTokenKey=RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenTypeConstant.accessToken);
        String refreshTokenKey=RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenTypeConstant.refreshToken);
        System.out.println("存入redis");
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);


        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success().setData(tokenResponse);
    }
}
