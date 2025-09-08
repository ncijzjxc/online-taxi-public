package com.mz.response;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 12:31
 * @Description: com.mz.response
 * @version: 6.0
 */
@Data
public class ForecastPriceResponse {
    private Double price;
    private String cityCode;
    private String vehicleType;
}
