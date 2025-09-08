package com.mz.controller;

import com.mz.dto.PriceRule;
import com.mz.dto.ResponseResult;
import com.mz.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/6 - 09 - 06 - 18:18
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {
    @Autowired
    PriceRuleService priceRuleService;
    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule){
        return priceRuleService.add(priceRule);
    }
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule){
        return priceRuleService.edit(priceRule);
    }
    @GetMapping("/if-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule){
        return priceRuleService.ifExists(priceRule);
    }
}
