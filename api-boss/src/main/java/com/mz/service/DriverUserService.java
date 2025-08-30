package com.mz.service;

import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 18:56
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DriverUserService {
    @Autowired
    ServiceUserClient serviceUserClient;
    public ResponseResult addDriverUser(DriverUser driverUser){
        serviceUserClient.addDriverUser(driverUser);
        return ResponseResult.success();
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        serviceUserClient.updateDriverUser(driverUser);
        return ResponseResult.success();
    }

}
