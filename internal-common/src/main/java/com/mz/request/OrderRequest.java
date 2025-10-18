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
    private Integer encrypt;
    /*编码方式*/
    private String fareType;
    /*计价版本*/
    private Integer fareVersion;
    /*用户设备唯一号*/
    private String deviceCode;
    /*订单id*/
    private String id;
    /*司机接乘客时时间*/
    private LocalDateTime toPickUpPassengerTime;
    /*司机接乘客时纬度*/
    private String toPickUpPassengerLatitude;
    /*司机接乘客时经度*/
    private String toPickUpPassengerLongitude;
    /*司机接乘客时地址*/
    private String toPickUpPassengerAddress;
    /*司机接到乘客时的经度*/
    private String pickUpPassengerLongitude;
    /*司机接到乘客时的纬度*/
    private String pickUpPassengerLatitude;
    /**
     * 乘客下车时间
     */
    private LocalDateTime passengerGetoffTime;
    /**
     * 乘客下车经度
     */
    private String passengerGetoffLongitude;
    /**
     * 乘客下车纬度
     */
    private String passengerGetoffLatitude;
    /**
     * 载客里程（米）
     */
    private Long driveMile;
    /**
     * 载客时间(分)
     */
    private Long driveTime;


}
