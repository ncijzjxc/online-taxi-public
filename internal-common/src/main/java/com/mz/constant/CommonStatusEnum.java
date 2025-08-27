package com.mz.constant;

import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 17:43
 * @Description: com.mz.controller.constant
 * @version: 6.0
 */

@AllArgsConstructor
@NoArgsConstructor

public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
    TOKEN_ERROR(1199,"token错误"),
    /*
    * 用户不存在*/
    USER_NOT_EXIST(1200,"用户不存在"),
    success(1,"success"),
    fail(0,"fail");
    @Getter
    private int  code;
    @Getter
    private String message;

}
