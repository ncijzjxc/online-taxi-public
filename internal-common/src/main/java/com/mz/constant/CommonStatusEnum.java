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
    /*验证码错误*/
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
    /*token错误*/
    TOKEN_ERROR(1199,"token错误"),
    /*
    * 用户不存在*/
    USER_NOT_EXIST(1200,"用户不存在"),
    /*计价服务为空*/
    PRICE_RULE_EMPTY(1399,"计价服务为空"),
    /*地图字典错误*/
    MAP_ERROR(1499,"地图字典错误"),
    /*车辆和车辆*/
    DRIVER_CAR_BIND_NOT_EXISTS(1500,"司机和车辆关系不存在"),
    DRIVER_NOT_EXISTS(1501,"司机不存在"),
    DRIVER_CAR_BIND_EXISTS(1502,"司机和车辆绑定关系已经存在，请勿重复绑定"),
    DRIVER_BIND_EXISTS(1503,"司机已经被重复绑定"),
    CAR_BIND_EXISTS(1504,"车辆已经被绑定"),
    SUCCESS(1,"success"),
    FAIL(0,"fail");
    @Getter
    private int  code;
    @Getter
    private String message;

}
