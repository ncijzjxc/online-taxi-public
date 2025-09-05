package com.mz.service;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.remote.ApiDriverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 19:47
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class ApiDriverService {
    @Autowired
    ApiDriverClient apiDriverClient;
    public ResponseResult updateDriverUser(DriverUser driverUser){
        LocalDateTime localDateTime=LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);
        return  apiDriverClient.updateDriverUser(driverUser);
    }
}
