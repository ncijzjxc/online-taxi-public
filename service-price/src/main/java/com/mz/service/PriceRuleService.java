package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.mz.constant.CommonStatusEnum;
import com.mz.dto.PriceRule;
import com.mz.dto.ResponseResult;
import com.mz.mapper.PriceRuleMapper;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: mz
 * @Date: 2025/9/6 - 09 - 06 - 18:21
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class PriceRuleService {
    @Autowired
    PriceRuleMapper priceRuleMapper;
    public ResponseResult add(PriceRule priceRule){
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType=cityCode+vehicleType;
        priceRule.setFareType(fareType);

        QueryWrapper<PriceRule> queryWrapper=new QueryWrapper();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion=0;
        if(priceRules.size()>0){
             return  ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());

        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");

    }
    public ResponseResult edit(PriceRule priceRule){

        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType=cityCode+vehicleType;
        priceRule.setFareType(fareType);

        QueryWrapper<PriceRule> queryWrapper=new QueryWrapper();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion=0;
        if(priceRules.size()>0){
            PriceRule rule = priceRules.get(0);
            fareVersion = rule.getFareVersion();
            Integer startFare = rule.getStartFare();
            Integer startMile = rule.getStartMile();
            Double unitPricePerMinute = rule.getUnitPricePerMinute();
            Double unitPricePerMile = rule.getUnitPricePerMile();

            if(startFare.equals(priceRule.getStartFare())&&startMile.equals(priceRule.getStartMile())
            && unitPricePerMile.equals(priceRule.getUnitPricePerMile())&&unitPricePerMinute.equals(priceRule.getUnitPricePerMinute())){
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NO_CHANE.getCode(),CommonStatusEnum.PRICE_RULE_NO_CHANE.getMessage());
            }

        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");

    }

    public ResponseResult<Boolean> ifExists(PriceRule priceRule){
        QueryWrapper<PriceRule> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("city_code",priceRule.getCityCode());
        queryWrapper.eq("vehicle_type",priceRule.getVehicleType());
        queryWrapper.orderByDesc("fare_type");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NO_EXISTS.getCode(),CommonStatusEnum.PRICE_RULE_NO_EXISTS.getMessage(),false);
        }
        return ResponseResult.success(true);
    }
}
