package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.request.ApiDriverPointRequest;
import com.mz.response.PointResponse;
import com.mz.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 20:34
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    PointsService pointsService;
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest){
        return pointsService.upload(apiDriverPointRequest);
    }
}
