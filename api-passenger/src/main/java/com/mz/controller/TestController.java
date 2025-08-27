package com.mz.controller;

import com.mz.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 20:35
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class TestController {
    @GetMapping("/auto")
    public ResponseResult getAuto(){
        return ResponseResult.success("auto test success");
    }
}
