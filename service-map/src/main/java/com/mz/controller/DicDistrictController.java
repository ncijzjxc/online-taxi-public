package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/8/29 - 08 - 29 - 15:17
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class DicDistrictController {
    @Autowired
    DicDistrictService dicDistrictService;
    @GetMapping("/dic_district")
    public ResponseResult initDicDistrict(String keyWords){
        return dicDistrictService.initDicDistrict(keyWords);
    }
}
