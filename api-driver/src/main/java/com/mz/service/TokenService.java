package com.mz.service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.TokenTypeConstant;
import com.mz.dto.ResponseResult;
import com.mz.dto.TokenResult;
import com.mz.response.TokenResponse;
import com.mz.util.JwtUtil;
import com.mz.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: mz
 * @Date: 2025/8/25 - 08 - 25 - 20:46
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class TokenService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    public ResponseResult refreshToken(String token){
        //解析token
        TokenResult tokenResult = JwtUtil.parseToken(token);
        if(tokenResult==null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        //生成key
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        String refreshKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenTypeConstant.refreshToken);
        //从redis中获取数据
        String redisToken= stringRedisTemplate.opsForValue().get(refreshKey);
        //对比数据
        if(StringUtils.isBlank(redisToken)|| !redisToken.trim().equals(token)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        //生成新的Token
        String accessKey=RedisPrefixUtils.generatorTokenKey(phone,identity,TokenTypeConstant.accessToken);
        String accessToken=JwtUtil.generatorToken(phone,identity,TokenTypeConstant.accessToken);
        String refreshToken=JwtUtil.generatorToken(phone,identity,TokenTypeConstant.refreshToken);
        stringRedisTemplate.opsForValue().set(accessKey,accessToken,29, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshKey,refreshToken,31, TimeUnit.DAYS);

        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success().setData(tokenResponse);
    }
}
