package com.mz.service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mz.constant.AmpConfigConstant;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.DriverCarConstant;
import com.mz.dto.DriverResponse;
import com.mz.dto.DriverUser;
import com.mz.dto.DriverUserWorkStatus;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DriverUserMapper;
import com.mz.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;
    /*添加司机*/
    public ResponseResult insertDriverUser(DriverUser driverUser){
        LocalDateTime localDateTime=LocalDateTime.now();
        driverUser.setGmtCreate(localDateTime);
        driverUser.setGmtModified(localDateTime);
        int result = driverUserMapper.insert(driverUser);
        //初始司机状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstant.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(localDateTime);
        driverUserWorkStatus.setGmtModified(localDateTime);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success(result);
    }
    /*修改司机信息*/

    public ResponseResult updateDriverUser(DriverUser driverUser){
        LocalDateTime localDateTime=LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }
    /*查询司机状态*/

    public  ResponseResult<DriverResponse> getDriverUserByPhone(String driverPhone){
        Map<String,Object> map=new HashMap<>();
        map.put("driver_phone",driverPhone);
        DriverResponse driverResponse=new DriverResponse();
        driverResponse.setDriverPhone(driverPhone);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if(driverUsers.isEmpty()){
            driverResponse.setIsExists(AmpConfigConstant.DRIVER_NO_EXISTS);
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage(),driverResponse);
        }
        if(driverUsers.get(0).getState().equals(DriverCarConstant.DRIVER_CAR_UNBIND)){
            driverResponse.setIsExists(AmpConfigConstant.DRIVER_NO_EXISTS);
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage(),driverResponse);
        }
        driverResponse.setIsExists(AmpConfigConstant.DRIVER_EXISTS);
        return ResponseResult.success(driverResponse);
    }
}
