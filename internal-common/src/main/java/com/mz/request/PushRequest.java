package com.mz.request;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 23:54
 * @Description: com.mz.request
 * @version: 6.0
 */

import lombok.Data;

@Data
public class PushRequest {
    private Long userId;
    private String identity;
    private String content;
}
