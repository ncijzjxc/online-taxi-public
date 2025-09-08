package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.OrderConstant;
import com.mz.dto.ForecastPriceDto;
import com.mz.dto.OrderInfo;
import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.mapper.OrderMapper;
import com.mz.remote.ServiceDriverUserClient;
import com.mz.remote.ServiceMapClient;
import com.mz.remote.ServicePriceClient;
import com.mz.request.OrderRequest;
import com.mz.response.ForecastPriceResponse;
import com.mz.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 21:48
 * @Description: com.mz
 * @version: 6.0
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ServicePriceClient servicePriceClient;
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;
    @Autowired
    ServiceMapClient serviceMapClient;
    public ResponseResult addOrder(OrderRequest orderRequest){


        //判断用户是否是黑名单
        String deviceCode = orderRequest.getDeviceCode();
        String key= RedisPrefixUtils.deviceCode+deviceCode;
        if (isExist(key))
            return ResponseResult.fail(CommonStatusEnum.PASSENGER_IS_BLACk.getCode(), CommonStatusEnum.PASSENGER_IS_BLACk.getMessage());

        //判断是否可以下订单
        Long orderGoing = isOrderGoing(orderRequest.getPassengerId());
        if(orderGoing>0){
            return ResponseResult.fail(CommonStatusEnum.PASSENGER_NO_ORDER.getCode(),CommonStatusEnum.PASSENGER_NO_ORDER.getMessage());
        }
        //判断当前city是否有司机
        ResponseResult<Boolean> booleanResponseResult = serviceDriverUserClient.checkCityDriver(orderRequest.getAddress());
        if(!booleanResponseResult.getData()){
            return ResponseResult.fail(CommonStatusEnum.CITY_NO_DRIVER.getCode(),CommonStatusEnum.CITY_NO_DRIVER.getMessage());
        }
        //创建订单

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);
        //查询计价
        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDestLatitude(orderRequest.getDestLatitude());
        forecastPriceDto.setDestLongitude(orderInfo.getDestLongitude());
        forecastPriceDto.setDepLatitude(orderInfo.getDepLatitude());
        forecastPriceDto.setDepLongitude(orderInfo.getDepLongitude());
        forecastPriceDto.setVehicleType(orderInfo.getFareType());
        forecastPriceDto.setCityCode(orderInfo.getAddress());

        //查询计价规则
        ResponseResult<ForecastPriceResponse> result=servicePriceClient.checkPrice(forecastPriceDto);
        Double price = result.getData().getPrice();

        LocalDateTime localDateTime=LocalDateTime.now();
        orderInfo.setPrice(String.valueOf(price));
        orderInfo.setGmtCreate(localDateTime);
        orderInfo.setGmtModified(localDateTime);
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        orderMapper.insert(orderInfo);


        //地图搜索车辆

        String depLongitude = orderRequest.getDepLongitude();
        String depLatitude = orderRequest.getDepLatitude();
        String center=depLatitude+","+depLongitude;
        Integer radius=2000;
        ResponseResult <List<TerminalResponse>> checkResults=serviceMapClient.checkCar(center,radius);
        log.info("第一次查询，半径为"+radius);
        if (checkResults.toString().isEmpty()){
            radius=4000;
            checkResults=serviceMapClient.checkCar(center,radius);
            log.info("第二次查询，半径为"+radius);
            if((checkResults.toString().isEmpty())){
                radius=5000;
                checkResults=serviceMapClient.checkCar(center,radius);
                log.info("第三次查询，半径为"+radius);
            }
        }


        return ResponseResult.success(price);


    }

    private boolean isExist(String key) {
        //先查询是否存在
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        if (aBoolean) {
            String s = stringRedisTemplate.opsForValue().get(key);
            int i = Integer.parseInt(s);
            if (i > 2) {
                return true;
            } else
                stringRedisTemplate.opsForValue().increment(key);
        }else
            stringRedisTemplate.opsForValue().setIfAbsent(key,"1",60,TimeUnit.MINUTES);
        return false;
    }

    public Long isOrderGoing(Long passengerId){
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("passenger_id",passengerId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstant.ORDER_START)
                .or().eq("order_status",OrderConstant.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstant.PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstant.PASSENGER_GETOFF)
                .or().eq("order_status",OrderConstant.TO_START_PAY)
                .or().eq("order_status",OrderConstant.SUCCESS_PAY));


        Long count = orderMapper.selectCount(queryWrapper);
        return count;
    }
}
