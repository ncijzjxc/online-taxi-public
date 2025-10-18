package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.DriverCarConstant;
import com.mz.dto.DriverCarBindingRelationship;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DriverCarBindingRelationshipMapper;
import javafx.beans.value.ObservableObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mz
 * @Date: 2025/8/31 - 08 - 31 - 22:30
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;
    /*司机和车辆绑定关系*/
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        /*查询司机和车辆是否绑定*/
        QueryWrapper<DriverCarBindingRelationship> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstant.DRIVER_CAR_BIND);
        Integer i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if( i.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());

        }
        /*查询司机是否绑定*/
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverCarConstant.DRIVER_CAR_BIND);
        i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if( i.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_BIND_EXISTS.getMessage());

        }
        /*查询车辆是否绑定*/
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstant.DRIVER_CAR_BIND);
        i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if( i.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(),CommonStatusEnum.CAR_BIND_EXISTS.getMessage());

        }

        /*开始绑定*/
        LocalDateTime localDateTime=LocalDateTime.now();
        driverCarBindingRelationship.setDriverId(driverCarBindingRelationship.getDriverId());
        driverCarBindingRelationship.setCarId(driverCarBindingRelationship.getCarId());
        driverCarBindingRelationship.setBindingTime(localDateTime);
        driverCarBindingRelationship.setBindState(DriverCarConstant.DRIVER_CAR_BIND);
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success();
    }
    /*司机和车辆解绑*/

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship){
        LocalDateTime localDateTime=LocalDateTime.now();
        Map<String , Object> map=new HashMap<>();
        map.put("driver_id",driverCarBindingRelationship.getDriverId());
        map.put("car_id",driverCarBindingRelationship.getCarId());
        map.put("bind_state",DriverCarConstant.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        if(driverCarBindingRelationships.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());

        }
        DriverCarBindingRelationship id= driverCarBindingRelationships.get(0);
        id.setUnBindingTime(localDateTime);
        id.setBindState(DriverCarConstant.DRIVER_CAR_UNBIND);
        driverCarBindingRelationshipMapper.updateById(id);

        return ResponseResult.success();
    }

}
