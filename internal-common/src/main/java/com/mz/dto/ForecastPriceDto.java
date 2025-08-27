package com.mz.dto;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 12:28
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
public class ForecastPriceDto {
    /*出发地经度*/
    private String depLongitude;
    /*出发地维度*/
    private String depLatitude;
    /*目的地经度*/
    private String destLongitude;
    /*目的地维度*/
    private String destLatitude;
}
