package com.mz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (PassengerUser)实体类
 *
 * @author makejava
 * @since 2025-08-23 16:58:21
 */
@Data

public class  PassengerUser implements Serializable {
    private static final long serialVersionUID = -75538538616259779L;

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

    private String profilePhoto;
}

