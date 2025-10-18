package com.mz.dto;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/9/3 - 09 - 03 - 16:00
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
public class TerminalResponse {
    private String tid;
    private Long carId;
    private String longitude;
    private String latitude;
    private String distance;
    private Long time;
}
