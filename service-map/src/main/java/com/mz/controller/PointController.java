package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.response.PointResponse;
import com.mz.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 15:12
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    PointService pointService;
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointResponse pointResponse){
        return pointService.upload(pointResponse);
    }
}
