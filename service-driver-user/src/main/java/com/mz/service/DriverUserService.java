package com.mz.service;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 16:20
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DriverUserService {
    @Autowired
    DriverUserMapper driverUserMapper;
    public ResponseResult insertDriverUser(DriverUser driverUser){
        LocalDateTime localDateTime=LocalDateTime.now();
        driverUser.setGmtCreate(localDateTime);
        driverUser.setGmtModified(localDateTime);
        int result = driverUserMapper.insert(driverUser);
        return ResponseResult.success(result);
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        LocalDateTime localDateTime=LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }
}
