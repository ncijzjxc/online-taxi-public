package com.mz.dto;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/24 - 08 - 24 - 15:36
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
public class TokenResult {
    public String phone;
    public String identity;
    public String type;
}
