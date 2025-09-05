package com.mz.controller;

import com.mz.dto.DriverUserWorkStatus;
import com.mz.dto.ResponseResult;
import com.mz.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 14:10
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
public class DriverUserWorkStatusController {
    @Autowired
    DriverUserWorkStatusService driverUserWorkStatusService;
    @PostMapping("/driver-user-work-status")
    public ResponseResult changeDriverStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){
        Long driverId = driverUserWorkStatus.getDriverId();
        Integer workStatus = driverUserWorkStatus.getWorkStatus();
        return driverUserWorkStatusService.changeDriverStatus(driverId,workStatus);
    }
}
