package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.remote.TrackClient;
import com.mz.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/3 - 09 - 03 - 23:28
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackService trackService;
    @PostMapping("/add")
    public ResponseResult addTrack(String tid){
        return trackService.addTrack(tid);
    }

}
