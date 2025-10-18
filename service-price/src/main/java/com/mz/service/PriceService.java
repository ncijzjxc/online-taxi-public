package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mz.constant.CommonStatusEnum;
import com.mz.dto.*;

import com.mz.mapper.PriceRuleMapper;
import com.mz.remote.ServiceMapClient;
import com.mz.response.ForecastPriceResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PipedReader;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @Auther: mz
 * @Date: 2025/8/27 - 08 - 27 - 12:32
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
@Slf4j
public class PriceService {
    @Autowired
    ServiceMapClient serviceMapClient;
    @Autowired
    PriceRuleMapper priceRuleMapper;
    /*预估价格*/
    public ResponseResult getPriceByMap( ForecastPriceDto forecastPriceDto){

        log.info("查询地图服务：");
        ResponseResult<DirectionDrivingResponse> distanceAndDuration = serviceMapClient.getDistanceAndDuration(forecastPriceDto);
        Integer distance = distanceAndDuration.getData().getDistance();
        Integer duration = distanceAndDuration.getData().getDuration();

        log.info("查询计价规则 ");

        QueryWrapper<PriceRule> queryWrapper =new QueryWrapper();
        queryWrapper.eq("city_code",forecastPriceDto.getCityCode());
        queryWrapper.eq("vehicle_type",forecastPriceDto.getVehicleType());
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.isEmpty()){
        return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }
        PriceRule priceRule = priceRules.get(0);
        Double price = getPrice(distance, duration, priceRule);


        ForecastPriceResponse forecastPriceResponse=new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(priceRule.getCityCode());
        forecastPriceResponse.setVehicleType(priceRule.getVehicleType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());
        return ResponseResult.success(forecastPriceResponse);
    }
    /*实际价格*/

    public ResponseResult getCalculatePrice(PriceDto priceDto){
        QueryWrapper<PriceRule> queryWrapper =new QueryWrapper();
        queryWrapper.eq("city_code",priceDto.getAddress());
        queryWrapper.eq("vehicle_type",priceDto.getVehicleType());
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }
        PriceRule priceRule = priceRules.get(0);
        Integer distance=priceDto.getDistance();
        Integer  duration=priceDto.getTime();
        Double price = getPrice(distance, duration, priceRule);
        return ResponseResult.success(price);
    }
    /*查询价格*/
    private  static Double getPrice(Integer distance ,Integer duration,PriceRule priceRule){
        //起步价格
        BigDecimal price=new BigDecimal(0);
        Integer startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal=new BigDecimal(startFare);
        price= price.add(startFareDecimal);
        //总公里数m
        BigDecimal distanceDecimal=new BigDecimal(distance);
        //总公里数 km
        BigDecimal distanceMileDecimal=distanceDecimal.divide(new BigDecimal(1000),2, ROUND_HALF_UP);
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileDecimal=new BigDecimal(startMile);
        Double subtract = distanceMileDecimal.subtract(startMileDecimal).doubleValue();

        Double mile=subtract<0?0:subtract;
        BigDecimal mileDecimal=new BigDecimal(mile);
        //里程总价
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal=new BigDecimal(unitPricePerMile);
        BigDecimal mileFare=unitPricePerMileDecimal.multiply(mileDecimal).setScale(2, ROUND_HALF_UP);
        price=price.add(mileFare);

        //计算时间价格

        BigDecimal timePrice=new BigDecimal(duration);
        BigDecimal timePriceDecimal=timePrice.divide(new BigDecimal(60),2, ROUND_HALF_UP);
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteDecimal=new BigDecimal(unitPricePerMinute);
        BigDecimal timeFare=timePriceDecimal.multiply(unitPricePerMinuteDecimal);
        price=price.add(timeFare);
        return price.doubleValue();
    }
}
