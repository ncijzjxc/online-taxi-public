package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: mz
 * @Date: 2025/8/26 - 08 - 26 - 13:29
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/users/")
    public ResponseResult getUser(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("authorization");
        System.out.println("获取到的token:"+authorization);
        return  userService.getUserByAccessToken(authorization);
    }
}
