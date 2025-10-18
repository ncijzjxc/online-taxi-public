package com.mz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: mz
 * @Date: 2025/10/18 - 10 - 18 - 22:55
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private Integer distance;
    private Integer time;
    private String address;
    private String vehicleType;
}
