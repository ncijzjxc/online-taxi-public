package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mz.constant.AmpConfigConstant;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.DriverCarConstant;
import com.mz.dto.*;
import com.mz.mapper.CarMapper;
import com.mz.mapper.DriverCarBindingRelationshipMapper;
import com.mz.mapper.DriverUserMapper;
import com.mz.mapper.DriverUserWorkStatusMapper;
import com.mz.response.OrderDriverResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DriverUserService {
    @Autowired
    DriverUserMapper driverUserMapper;
    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;
    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;
    @Autowired
    CarMapper carMapper;

    /*添加司机*/
    public ResponseResult insertDriverUser(DriverUser driverUser) {
        LocalDateTime localDateTime = LocalDateTime.now();
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

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime localDateTime = LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }
    /*查询司机状态*/

    public ResponseResult<DriverResponse> getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setDriverPhone(driverPhone);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()) {
            driverResponse.setIsExists(AmpConfigConstant.DRIVER_NO_EXISTS);
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage(), driverResponse);
        }
        if (driverUsers.get(0).getState().equals(DriverCarConstant.DRIVER_CAR_UNBIND)) {
            driverResponse.setIsExists(AmpConfigConstant.DRIVER_NO_EXISTS);
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage(), driverResponse);
        }
        driverResponse.setIsExists(AmpConfigConstant.DRIVER_EXISTS);
        return ResponseResult.success(driverResponse);
    }


    public ResponseResult<OrderDriverResponse> availableDriver(Long carId) {
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", carId);
        queryWrapper.eq("bind_state", DriverCarConstant.DRIVER_CAR_BIND);

        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        Long driverId = driverCarBindingRelationship.getDriverId();
        // 司机工作状态的查询
        QueryWrapper<DriverUserWorkStatus> driverUserWorkStatusQueryWrapper = new QueryWrapper<>();
        driverUserWorkStatusQueryWrapper.eq("driver_id",driverId);
        driverUserWorkStatusQueryWrapper.eq("work_status",DriverCarConstant.DRIVER_WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(driverUserWorkStatusQueryWrapper);
        if(null==driverUserWorkStatus) {
            log.info("没有司机可用");
            return ResponseResult.fail(CommonStatusEnum.NO_DRIVER_AVAILABLE.getCode(), CommonStatusEnum.NO_DRIVER_AVAILABLE.getMessage());
        }else {
            //查询司机信息
            QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
            driverUserQueryWrapper.eq("id", driverId);
            DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);

            //查询车辆信息
            QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
            carQueryWrapper.eq("id",carId);
            Car car = carMapper.selectOne(carQueryWrapper);

            String driverPhone = driverUser.getDriverPhone();
            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverPhone(driverPhone);
            orderDriverResponse.setLicenseId(driverUser.getLicenseId());
            orderDriverResponse.setVehicleNo(car.getVehicleNo());
            orderDriverResponse.setVehicleType(car.getVehicleType());


            return ResponseResult.success( orderDriverResponse);
        }

    }
}
