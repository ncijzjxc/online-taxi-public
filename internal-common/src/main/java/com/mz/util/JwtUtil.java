package com.mz.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mz.constant.TokenTypeConstant;
import com.mz.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 14:14
 * @Description: com.mz
 * @version: 6.0
 */
public class JwtUtil {
    private static final String SIGN="2efdsdr32@";
    private static final String TOKEN_KEY="phone";
    private static final String TOKEN_IDENTITY="identity";
    private static final String TOKEN_TYPE="tokenType";
    private static final String TOKEN_TIME="tokenTime";
    public static String generatorToken(String passengerPhone,String identity,String Type){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date=calendar.getTime();

        //生成token
        Map<String,String > map=new HashMap<>();
        map.put(TOKEN_KEY,passengerPhone);
        map.put(TOKEN_IDENTITY,identity);
        map.put(TOKEN_TYPE,Type);

        map.put(TOKEN_TIME,date.toString());
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
       });
        //将日期加入jwt;
        //builder.withExpiresAt(date);
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        System.out.println("生成的Token:"+sign);
        return sign;
    }
    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(TOKEN_KEY).asString();
        String identity= verify.getClaim(TOKEN_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        tokenResult.setType(TokenTypeConstant.accessToken);
        return tokenResult;
    }
    //效验token
    public static TokenResult checkToken(String token){
        TokenResult tokenResult=null;
        try {
             tokenResult = parseToken(token);
        }catch (Exception e){
            System.out.println(" token valid");
        }
        return tokenResult;
    }
}
