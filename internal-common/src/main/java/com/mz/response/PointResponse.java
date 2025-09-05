package com.mz.response;

import com.mz.dto.PointDto;
import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 15:16
 * @Description: com.mz.response
 * @version: 6.0
 */
@Data
public class PointResponse {
    private String tid;
    private String trid;
    private PointDto[] points;
}
