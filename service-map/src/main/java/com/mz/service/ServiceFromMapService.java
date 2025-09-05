package com.mz.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 19:12
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class ServiceFromMapService {
    @Autowired
    ServiceClient serviceClient;
    public ResponseResult addService(String name){
        return serviceClient.serviceFromMap(name);
    }
}
