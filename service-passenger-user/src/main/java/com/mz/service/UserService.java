package com.mz.service;

import com.mz.constant.CommonStatusEnum;
import com.mz.dto.ResponseResult;
import com.mz.dto.PassengerUser;
import com.mz.dto.UserResponse;
import com.mz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: mz
 * @Date: 2025/8/23 - 08 - 23 - 15:53
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public ResponseResult loginUser(String passengerPhone){
        System.out.println("获取手机号");
        HashMap<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = userMapper.selectByMap(map);
        System.out.println("查询用户是否存在");
        String result = passengerUsers.isEmpty() ? "用户不存在" : passengerUsers.get(0).getPassengerPhone();

        if("用户不存在".equals(result)) {
            System.out.println("用户不存在,插入用户");
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张站");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setGmtModified(new Date());
            passengerUser.setGmtCreate(new Date());
            passengerUser.setState((byte) 0);
            userMapper.insert(passengerUser);
            result=passengerPhone;

        }
        return ResponseResult.success(result);
    }
    public ResponseResult getUserByPhone(String passengerPhone){

        HashMap<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = userMapper.selectByMap(map);
        if(passengerUsers.size()==0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(),CommonStatusEnum.USER_NOT_EXIST.getMessage());
        }else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
