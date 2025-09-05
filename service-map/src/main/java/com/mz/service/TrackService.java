package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 12:32
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class TrackService {
    @Autowired
    TrackClient trackClient;
    public ResponseResult addTrack(String tid){
        return trackClient.addTrack(tid);
    }
}
