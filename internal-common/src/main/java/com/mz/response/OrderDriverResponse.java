package com.mz.response;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/9/9 - 09 - 09 - 15:59
 * @Description: com.mz.response
 * @version: 6.0
 */
@Data
public class OrderDriverResponse {
    private Long driverId;
    private String driverPhone;
    private Long carId;
    private String licenseId;
    private String vehicleNo;
    private String vehicleType;


}
