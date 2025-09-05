package com.mz.controller;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.request.VerifcationCodeDTO;
import com.mz.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/1 - 09 - 01 - 15:02
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class VerificationCodeController {
    @Autowired
    VerificationCodeService verificationCodeService;
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerifcationCodeDTO verifcationCodeDTO){
        String driverPhone = verifcationCodeDTO.getDriverPhone();
        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }
    @PostMapping("/verification-code-check")
    public ResponseResult checkCode(@RequestBody VerifcationCodeDTO verifcationCodeDTO){
        String driverPhone = verifcationCodeDTO.getDriverPhone();
        String code = verifcationCodeDTO.getNumberCode();
        return verificationCodeService.checkCode(driverPhone,code);
    }


}
