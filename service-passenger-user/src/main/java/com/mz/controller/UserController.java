package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.request.VerifcationCodeDTO;
import com.mz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/8/23 - 08 - 23 - 15:51
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/user")
    public ResponseResult loginUser(@RequestBody VerifcationCodeDTO verifcationCodeDTO){
        System.out.println("用户的手机号是:"+verifcationCodeDTO.getPassengerPhone());
        return userService.loginUser(verifcationCodeDTO.getPassengerPhone());
    }
    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone")String passengerPhone){
        System.out.println(passengerPhone);
        return userService.getUserByPhone(passengerPhone);
    }
}
