package com.mz.interceptor;

import com.alibaba.nacos.api.utils.StringUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mz.dto.ResponseResult;
import com.mz.dto.TokenResult;
import com.mz.util.JwtUtil;
import com.mz.util.RedisPrefixUtils;
import lombok.AllArgsConstructor;
import net.sf.json.JSONObject;
import org.aopalliance.intercept.Interceptor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Enumeration;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 17:10
 * @Description: com.mz.interceptor
 * @version: 6.0
 */

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result=true;
        String resultString=null;
        TokenResult tokenResult=null;

        String authorization = request.getHeader("authorization");

        tokenResult=JwtUtil.checkToken(authorization);


        //对redis进行效验
        if(tokenResult==null){
            result = false;
            resultString="token valid";
        }else {
            //从redis中取数据
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String type = tokenResult.getType();
            String key = RedisPrefixUtils.generatorTokenKey(phone,identity,type);
            String token = stringRedisTemplate.opsForValue().get(key);
            if(StringUtils.isBlank(token)||(!token.trim().equals(authorization.trim()))){
                result = false;
                resultString="token valid";
            }
        }

        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());

        }
        return result;
    }
}
