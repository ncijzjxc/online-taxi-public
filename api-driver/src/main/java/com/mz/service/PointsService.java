package com.mz.service;

import com.mz.dto.Car;
import com.mz.dto.ResponseResult;
import com.mz.remote.ApiDriverClient;
import com.mz.remote.ServiceMapClient;
import com.mz.request.ApiDriverPointRequest;
import com.mz.response.PointResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 20:48
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class PointsService {
    @Autowired
    ApiDriverClient apiDriverClient;
    @Autowired
    ServiceMapClient serviceMapClient;
    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest){
        Long carId = apiDriverPointRequest.getCarId();
        ResponseResult<Car> result = apiDriverClient.getCarById(carId);
        Car car=result.getData();
        String tid = car.getTid();
        String trid = car.getTrid();
        PointResponse pointResponse=new PointResponse();
        pointResponse.setTid(tid);
        pointResponse.setTrid(trid);
        pointResponse.setPoints(apiDriverPointRequest.getPoints());
        return serviceMapClient.upload(pointResponse);

    }
}
