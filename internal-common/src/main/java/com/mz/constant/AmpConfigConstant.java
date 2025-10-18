package com.mz.constant;

import com.mz.response.ForecastPriceResponse;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 22:44
 * @Description: com.mz.constant
 * @version: 6.0
 */
public class AmpConfigConstant {
    /*调用高德地图服务预估距离和时间*/
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";
    /*调用字典服务*/
    public static final String DISTRICT_URL="https://restapi.amap.com/v3/config/district";
    /*高德猎鹰服务添加服务*/
    /*搜索轨迹点*/
    public static final  String TERMINAL_TRSEARCH="https://tsapi.amap.com/v1/track/terminal/trsearch";

    public static final String SERVICE_ADD_URL="https://tsapi.amap.com/v1/track/service/add";
    /*高德猎鹰服务添加终端*/
    public static final String TERMINAL_ADD_URL="https://tsapi.amap.com/v1/track/terminal/add";
    /*高德猎鹰服务添加终端轨迹*/
    public static final String TRACK_ADD_URL="https://tsapi.amap.com/v1/track/trace/add";
    /*高德猎鹰服务添加轨迹位置*/
    public static final String POINT_UPLOAD_URL="https://tsapi.amap.com/v1/track/point/upload";
    /*高德地图周边搜索车辆*/
    public static final String TERMINAL_AROUND_SEARCH="https://tsapi.amap.com/v1/track/terminal/aroundsearch";
    public static final String STATUS="status";
    public static final  String ROUTE="route";
    public static final  String PATHS="paths";
    public static final  String DISTANCE="distance";
    public static final  String DURATION="duration";

    public static final  String ADDRESS_CODE="adcode";
    public static final String DISTRICTS="districts";
    public static final String ADDRESS_NAME="name";
    public static final String LEVEL="level";

    public static final String STREET="street";
    public static final Integer DRIVER_EXISTS=1;
    public static final Integer DRIVER_NO_EXISTS=2;
    public static final String DEVICE_C0DE="deviceCode";

}
