package com.mz.controller;

import com.mz.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/1 - 09 - 01 - 22:58
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseResult test(){
        return ResponseResult.success("sdas");
    }
}
