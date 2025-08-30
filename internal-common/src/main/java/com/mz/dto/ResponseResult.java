package com.mz.dto;

import com.mz.constant.CommonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther: mz
 * @Date: 2025/8/21 - 08 - 21 - 17:45
 * @Description: com.mz.controller.dto
 * @version: 6.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseResult<T>{
    private int  code;
    private String message;
    private T data;

    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage());

    }
    public static <T> ResponseResult success(T data){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage()).setData(data);

    }
   public static <T> ResponseResult fail(int code ,String message ,T data){
        return  new ResponseResult().setCode(code).setMessage(message).setData(data);

   }
   public static <T> ResponseResult fail(int code,String message){
        return new ResponseResult().setCode(code).setMessage(message);

   }
   public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
   }
}
