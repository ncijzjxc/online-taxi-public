package com.mz.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (DriverUserWorkStatus)实体类
 *
 * @author makejava
 * @since 2025-09-02 14:04:41
 */
@Data
public class DriverUserWorkStatus implements Serializable {
    private static final long serialVersionUID = -38249748921675081L;

    private Long id;

    private Long driverId;

    private Integer workStatus;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


}

