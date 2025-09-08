package com.mz.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 18:02
 * @Description: com.mz.request
 * @version: 6.0
 */
@Data
public class OrderRequest {
    /*乘客id*/
    private Long passengerId;
    /*乘客手机号*/
    private String passengerPhone;
    /*下单地区*/
    private String address;
    /*下单时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;
    /*订单时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    /*下单地方*/
    private String departure;
    /*下单经度*/
    private String depLongitude;
    /*下单纬度*/
    private String depLatitude;
    /*下单目的地*/
    private String destination;
    /*下单目的地经度*/
    private String destLongitude;
    /*下单目的地纬度*/
    private String destLatitude;
    /*坐标加密*/
    private Integer encrpt;
    /*编码方式*/
    private String fareType;
    /*计价版本*/
    private Integer fareVersion;
    /*用户设备唯一号*/
    private String deviceCode;

}
