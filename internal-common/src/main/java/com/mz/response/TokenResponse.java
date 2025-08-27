package com.mz.response;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/22 - 08 - 22 - 10:34
 * @Description: com.mz.response
 * @version: 6.0
 */
@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
