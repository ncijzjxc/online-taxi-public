package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.request.VerifcationCodeDTO;
import com.mz.service.VerifcationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 12:37
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class VerifcationCodeController {
    @Autowired
    VerifcationCodeService verifcationCodeService;
    /*
    生成验证码
    * */
    @GetMapping("/verifcation-code")
    public ResponseResult generatorCode(@RequestBody VerifcationCodeDTO verifcationCodeDTO){
        String passengerPhone = verifcationCodeDTO.getPassengerPhone();
        return verifcationCodeService.generatorCode(passengerPhone);
    }
    /*
    * 验证码效验*/
    @PostMapping("/verifcation-code-check")
    public ResponseResult checkCode(@RequestBody VerifcationCodeDTO verifcationCodeDTO){
        String passengerPhone = verifcationCodeDTO.getPassengerPhone();
        String code = verifcationCodeDTO.getNumberCode();
        return verifcationCodeService.checkCode(passengerPhone,code);
    }
    /*
    * 判断用户是否存在*/
}
