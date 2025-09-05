package com.mz.service;

import com.mz.dto.Car;
import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.dto.TrackResponse;
import com.mz.mapper.CarMapper;
import com.mz.remote.ServiceMapClient;
import com.mz.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 23:59
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class CarService {
    @Autowired
    CarMapper carMapper;
    @Autowired
    ServiceMapClient serviceMapClient;

    @Transactional
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtModified(now);
        car.setGmtCreate(now);
        // 保存车辆
        carMapper.insert(car);

        // 获得此车辆的终端id：tid
        String desc=car.getId();
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo(), desc);
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        // 获得此车辆的轨迹id：trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();

        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.updateById(car);


        return ResponseResult.success("");
    }

    public ResponseResult getCarById(Long carId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", carId);
        List<Car> cars = carMapper.selectByMap(map);
        return ResponseResult.success(cars.get(0));
    }
}
