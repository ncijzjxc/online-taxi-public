package com.mz.util;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 20:19
 * @Description: com.mz.util
 * @version: 6.0
 */
public class RedisPrefixUtils {
    private static  String   verifcationCodePrefix="passenger-verification-code:";
    private static String  tokenPrefix="token-";
    /*
     * 生成获取验证码存入redis中的key*/
    public static String generatorKeyByPhone(String passengerPhone){
        return verifcationCodePrefix+passengerPhone;

    }
    /*
     * 生成token存入redis中的key*/

    public static String generatorTokenKey(String phone, String identity ,String Type){
        return tokenPrefix+phone+"-"+identity+"-"+Type;
    }
}
