package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.response.TokenResponse;
import com.mz.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/25 - 08 - 25 - 20:43
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class TokenController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        return  tokenService.refreshToken(refreshToken);
    }
}
