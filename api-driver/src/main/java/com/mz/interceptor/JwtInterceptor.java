package com.mz.interceptor;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mz.dto.ResponseResult;
import com.mz.dto.TokenResult;
import com.mz.util.JwtUtil;
import com.mz.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        
        // 检查token是否为空
        if (StringUtils.isBlank(authorization)) {
            result = false;
            resultString = "token is empty";
        } else {
            tokenResult = JwtUtil.checkToken(authorization);
            
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
        }

        if(!result){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
            out.flush();
        }
        return result;
    }
}
