package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 15:22
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class ServsionVerificationCodeController {
    @GetMapping("/numberCode/{size}")
    public ResponseResult getCode(@PathVariable("size")Integer size){
        double mathRandom= (Math.random() * 9 + 1) * (Math.pow(10, size-1));
        System.out.println("生成的原始数据是："+mathRandom);
        int resultInt=(int) mathRandom;
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(resultInt);
        return ResponseResult.success(numberCodeResponse);
    }
}
