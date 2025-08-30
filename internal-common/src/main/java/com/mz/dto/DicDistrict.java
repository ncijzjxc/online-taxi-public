package com.mz.dto;

import lombok.Data;

/**
 * @Auther: mz
 * @Date: 2025/8/29 - 08 - 29 - 15:09
 * @Description: com.mz.dto
 * @version: 6.0
 */
@Data
public class DicDistrict {
    private String addressCode;
    private String addressName;
    private String ParentAddressCode;
    private Integer Level;
}
