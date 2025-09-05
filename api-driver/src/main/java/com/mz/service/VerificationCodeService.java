package com.mz.service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.DriverCarConstant;
import com.mz.constant.IdentityConstant;
import com.mz.constant.TokenTypeConstant;
import com.mz.dto.DriverResponse;
import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.remote.ApiDriverClient;
import com.mz.remote.ServisonVerificationCodeClient;
import com.mz.request.VerifcationCodeDTO;
import com.mz.response.NumberCodeResponse;
import com.mz.response.TokenResponse;
import com.mz.util.JwtUtil;
import com.mz.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mz
 * @Date: 2025/9/1 - 09 - 01 - 15:14
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
@Slf4j
public class VerificationCodeService {
    @Autowired
    ServisonVerificationCodeClient servisonVerificationCodeClient;
    @Autowired
    ApiDriverClient apiDriverClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndSendVerificationCode(String driverPhone){

        //判断司机是否存在
        ResponseResult<DriverResponse> driverResponseResponseResult = apiDriverClient.checkDriverUserExists(driverPhone);
        Integer isExists = driverResponseResponseResult.getData().getIsExists();
        if(isExists.equals(DriverCarConstant.DRIVER_STATE_UN_VALID)){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
        }else {
            //获取验证码
            ResponseResult<NumberCodeResponse> login = servisonVerificationCodeClient.verificationCode(6);
            log.info("司机的号码是：" + driverPhone);
            int numberCode = login.getData().getNumberCode();
            String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.DRIVER_IDENTITY);
            stringRedisTemplate.opsForValue().set(key,numberCode+"",2,TimeUnit.MINUTES);

            return ResponseResult.success(numberCode);
        }
    }
    public ResponseResult checkCode(String driverPhone,String code){
        //从redis中获取验证码
        System.out.println("从redis中获取验证码");
        //生成key
        String key =RedisPrefixUtils.generatorKeyByPhone(driverPhone,IdentityConstant.DRIVER_IDENTITY);
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

        //生成令牌
        String accessToken = JwtUtil.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenTypeConstant.accessToken);
        String refreshToken=JwtUtil.generatorToken(driverPhone,IdentityConstant.DRIVER_IDENTITY,TokenTypeConstant.refreshToken);
        //存入redis中
        String accessTokenKey=RedisPrefixUtils.generatorTokenKey(driverPhone,IdentityConstant.DRIVER_IDENTITY,TokenTypeConstant.accessToken);
        String refreshTokenKey=RedisPrefixUtils.generatorTokenKey(driverPhone,IdentityConstant.DRIVER_IDENTITY,TokenTypeConstant.refreshToken);
        System.out.println("存入redis");
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);


        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success().setData(tokenResponse);
    }
}
