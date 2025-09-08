package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/8 - 09 - 08 - 17:47
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class CityDriverService {
    @Autowired
    DriverUserMapper driverUserMapper;
    public ResponseResult<Boolean> checkCityDriver(String cityCode){
        int count = driverUserMapper.checkCityDriver(cityCode);
        if(count<=0){
            return ResponseResult.fail(false);
        }
        return ResponseResult.success(true);
    }
}
