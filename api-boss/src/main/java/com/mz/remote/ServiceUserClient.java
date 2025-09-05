package com.mz.remote;

import com.mz.dto.Car;
import com.mz.dto.DriverCarBindingRelationship;
import com.mz.dto.DriverUser;
import com.mz.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 18:56
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-driver-user")
public interface ServiceUserClient {
    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);
    @RequestMapping(method = RequestMethod.PUT,value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
    @RequestMapping(method = RequestMethod.POST,value = "/car")
    public ResponseResult addCar(@RequestBody Car car);
    @RequestMapping(method = RequestMethod.POST,value = "/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    @RequestMapping(method = RequestMethod.POST,value = "/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);
}
