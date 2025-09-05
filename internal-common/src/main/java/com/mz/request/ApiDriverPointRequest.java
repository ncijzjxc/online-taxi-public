package com.mz.request;

import com.mz.dto.PointDto;
import com.mz.response.PointResponse;
import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 20:50
 * @Description: com.mz.request
 * @version: 6.0
 */
@Data
public class ApiDriverPointRequest {
    private Long carId;
    private PointDto[] points;
}
