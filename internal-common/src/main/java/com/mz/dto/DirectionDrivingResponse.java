package com.mz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 19:58
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDrivingResponse {
    private Integer distance;
    private Integer duration;
}
