package com.mz.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifcationCodeDTO {
    private String passengerPhone;
    private String  numberCode;
}