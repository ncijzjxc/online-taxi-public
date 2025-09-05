package com.mz.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (DriverCarBindingRelationship)实体类
 *
 * @author makejava
 * @since 2025-08-31 22:07:12
 */
@Data
public class DriverCarBindingRelationship implements Serializable {
    private static final long serialVersionUID = -69395729418288825L;

    private Long id;

    private Long driverId;

    private Long carId;

    private Integer bindState;

    private LocalDateTime bindingTime;

    private LocalDateTime unBindingTime;

}

