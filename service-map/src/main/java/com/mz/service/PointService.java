package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.remote.PointClient;
import com.mz.response.PointResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 15:50
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class PointService {
    @Autowired
    PointClient pointClient;
    public ResponseResult upload(PointResponse pointResponse){
        return pointClient.upload(pointResponse);
    }
}
