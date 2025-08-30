package com.mz.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DicDistrictMapper;
import com.mz.remote.DicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/8/29 - 08 - 29 - 15:19
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class DicDistrictService {
    @Autowired
    DicDistrictMapper dicDistrictMapper;
    @Autowired
    DicDistrictClient dicDistrictClient;
    public ResponseResult initDicDistrict(String keyWords){
        //获取字典
        dicDistrictClient.DicDistrict(keyWords);
        //解析结果

        //插入数据库
        return ResponseResult.success();
    }
}
