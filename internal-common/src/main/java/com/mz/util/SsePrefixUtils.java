package com.mz.util;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 23:56
 * @Description: com.mz.util
 * @version: 6.0
 */
public class SsePrefixUtils {

    public static  final String sperator = "$";

    public  static String generatorSseKey(Long userId , String identity){
        return userId+sperator+identity;
    }
}
