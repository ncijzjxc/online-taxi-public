package com.mz.dto;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/28 - 08 - 28 - 17:57
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
public class PriceRule {
    private String cityCode;
    private String vehicleType;
    private Integer startFare;
    private Integer startMile;
    private Double unitPricePerMile;
    private Double unitPricePerMinute;
    private Integer fareVersion;
    private String fareType;
}
