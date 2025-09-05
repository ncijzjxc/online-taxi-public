package com.mz.service;

import com.mz.dto.DriverUserWorkStatus;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DriverCarBindingRelationshipMapper;
import com.mz.mapper.DriverUserWorkStatusMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 14:06
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DriverUserWorkStatusService {

    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;
    public ResponseResult changeDriverStatus(Long driverId,Integer status){
        LocalDateTime localDateTime=LocalDateTime.now();
        Map<String ,Object> map=new HashMap<>();
        map.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(map);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
        driverUserWorkStatus.setWorkStatus(status);
        driverUserWorkStatus.setGmtCreate(localDateTime);
        driverUserWorkStatus.setGmtModified(localDateTime);
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        return ResponseResult.success("");
    }
}
