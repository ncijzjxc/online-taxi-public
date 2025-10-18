package com.mz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mz.config.RedisConfig;
import com.mz.constant.CommonStatusEnum;
import com.mz.constant.DriverCarConstant;
import com.mz.constant.IdentityConstant;
import com.mz.constant.OrderConstant;
import com.mz.dto.*;
import com.mz.mapper.OrderMapper;
import com.mz.remote.ServiceDriverUserClient;
import com.mz.remote.ServiceMapClient;
import com.mz.remote.ServicePriceClient;
import com.mz.remote.ServiceSsePushClient;
import com.mz.request.OrderRequest;
import com.mz.request.PushRequest;
import com.mz.response.OrderDriverResponse;
import com.mz.response.ForecastPriceResponse;
import com.mz.response.TrsearchResponse;
import com.mz.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;
import org.checkerframework.checker.units.qual.K;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: mz
 * @Date: 2025/9/5 - 09 - 05 - 21:48
 * @Description: com.mz
 * @version: 6.0
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ServicePriceClient servicePriceClient;
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;
    @Autowired
    ServiceMapClient serviceMapClient;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    ServiceSsePushClient serviceSsePushClient;
    /*派单*/

    public ResponseResult addOrder(OrderRequest orderRequest) {


        //判断用户是否是黑名单
        String deviceCode = orderRequest.getDeviceCode();
        String key = RedisPrefixUtils.deviceCode + deviceCode;
        if (isExist(key))
            return ResponseResult.fail(CommonStatusEnum.PASSENGER_IS_BLACk.getCode(), CommonStatusEnum.PASSENGER_IS_BLACk.getMessage());

        //判断是否可以下订单
        Long orderGoing = isOrderGoing(orderRequest.getPassengerId());
        if (orderGoing > 0) {
            return ResponseResult.fail(CommonStatusEnum.PASSENGER_NO_ORDER.getCode(), CommonStatusEnum.PASSENGER_NO_ORDER.getMessage());
        }
        //判断当前city是否有司机
        ResponseResult<Boolean> booleanResponseResult = serviceDriverUserClient.checkCityDriver(orderRequest.getAddress());
        if (!booleanResponseResult.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_NO_DRIVER.getCode(), CommonStatusEnum.CITY_NO_DRIVER.getMessage());
        }
        //创建订单

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderMapper.insert(orderInfo);

        //查询计价
        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDestLatitude(orderRequest.getDestLatitude());
        forecastPriceDto.setDestLongitude(orderInfo.getDestLongitude());
        forecastPriceDto.setDepLatitude(orderInfo.getDepLatitude());
        forecastPriceDto.setDepLongitude(orderInfo.getDepLongitude());
        forecastPriceDto.setVehicleType(orderInfo.getFareType());
        forecastPriceDto.setCityCode(orderInfo.getAddress());

        //查询计价规则
        ResponseResult<ForecastPriceResponse> result = servicePriceClient.checkPrice(forecastPriceDto);
        Double price = result.getData().getPrice();
        orderInfo.setPrice(String.valueOf(price));
        orderInfo.setFareVersion(result.getData().getFareVersion());



        //地图搜索车辆
        for(int i=0;i<6;i++){
            boolean flag = dispatchRealTimeOrder(orderInfo);
            if(flag)
                break;
            else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ResponseResult.success(price);
    }

    /* 进行派单*/
    private boolean dispatchRealTimeOrder(OrderInfo orderInfo) {
        boolean flag=false;
        String depLongitude = orderInfo.getDepLongitude();
        String depLatitude = orderInfo.getDepLatitude();
        String center = depLatitude + "," + depLongitude;
        List<Integer> radiuslist = new ArrayList<>();
        radiuslist.add(2000);
        radiuslist.add(4000);
        radiuslist.add(5000);

        boolean driverFound = false; // 添加标志位

        for (int i = 0; i < radiuslist.size(); i++) {
            Integer radius = radiuslist.get(i);
            ResponseResult<List<TerminalResponse>> checkResults = serviceMapClient.checkCar(center, radius);
            log.info("第" + (i + 1) + "次查询，半径为" + radius);
            log.info("查询结果为" + JSONArray.fromObject(checkResults.getData()));

            JSONArray jsonArray = JSONArray.fromObject(checkResults.getData());
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String carIdString = jsonObject.getString("carId");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");

                Long carId = Long.parseLong(carIdString);
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);

                if (availableDriver.getCode() == CommonStatusEnum.NO_DRIVER_AVAILABLE.getCode()) {
                    log.info("没有车辆ID：" + carId + "对应的司机");
                    continue;
                } else {
                    log.info("车辆ID：" + carId + "找到了正在出车的司机");

                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();

                    String key = (driverId + "").intern();
                    RLock lock = redissonClient.getLock(key);
                    lock.lock();

                    // 添加调试日志
                    Long goingDriverCount = isOrderGoingDriver(driverId);
                    log.info("司机ID：" + driverId + "，正在进行的订单数量：" + goingDriverCount);

                    if (goingDriverCount > 0) {
                        log.info("司机ID：" + driverId + "正在处理其他订单，跳过");
                        lock.unlock();
                        continue;
                    } else {
                        log.info("司机ID：" + driverId + "可用，开始设值");

                        LocalDateTime localDateTime = LocalDateTime.now();
                        orderInfo.setLicenseId(availableDriver.getData().getLicenseId());
                        orderInfo.setVehicleNo(availableDriver.getData().getVehicleNo());
                        orderInfo.setVehicleType(availableDriver.getData().getVehicleType());
                        orderInfo.setDriverPhone(availableDriver.getData().getDriverPhone());
                        orderInfo.setCarId(carId);
                        orderInfo.setDriverId(driverId);
                        orderInfo.setReceiveOrderCarLatitude(String.valueOf(latitude));
                        orderInfo.setReceiveOrderCarLongitude(String.valueOf(longitude));
                        orderInfo.setReceiveOrderTime(localDateTime);
                        orderInfo.setGmtCreate(localDateTime);
                        orderInfo.setGmtModified(localDateTime);
                        orderInfo.setOrderStatus(OrderConstant.DRIVER_RECEIVE_ORDER);
                        orderMapper.updateById(orderInfo);
                        //通知司机

                        JSONObject driverContent=new JSONObject();
                        driverContent.put("passenger_id",orderInfo.getPassengerId());
                        driverContent.put("passenger_phone",orderInfo.getPassengerPhone());
                        driverContent.put("departure",orderInfo.getDeparture());
                        driverContent.put("depLatitude",orderInfo.getDepLatitude());
                        driverContent.put("depLongitude",orderInfo.getDepLongitude());

                        driverContent.put("destination",orderInfo.getDestination());
                        driverContent.put("destLatitude",orderInfo.getDestLatitude());
                        driverContent.put("destLongitude",orderInfo.getDestLongitude());
                        send(driverId, IdentityConstant.DRIVER_IDENTITY,driverContent.toString());

                        //通知乘客
                        JSONObject passengerContent = new  JSONObject();
                        passengerContent.put("orderId",orderInfo.getId());
                        passengerContent.put("driverId",orderInfo.getDriverId());
                        passengerContent.put("driverPhone",orderInfo.getDriverPhone());
                        passengerContent.put("vehicleNo",orderInfo.getVehicleNo());
                        // 车辆信息，调用车辆服务
                        ResponseResult<Car> carById = serviceDriverUserClient.getCar(carId);
                        Car carRemote = carById.getData();

                        passengerContent.put("brand", carRemote.getBrand());
                        passengerContent.put("model",carRemote.getModel());
                        passengerContent.put("vehicleColor",carRemote.getVehicleColor());

                        passengerContent.put("receiveOrderCarLongitude",orderInfo.getReceiveOrderCarLongitude());
                        passengerContent.put("receiveOrderCarLatitude",orderInfo.getReceiveOrderCarLatitude());

                        send(orderInfo.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY, passengerContent.toString());
                        lock.unlock();



                        driverFound = true; // 设置找到司机标志
                        flag=true;
                        break; // 找到司机后立即退出内层循环
                    }
                }
            }

            if (driverFound) {
                break; // 找到司机后退出外层循环
            }
        }

        if (!driverFound) {
            log.warn("在所有半径范围内都未找到可用司机");
        }
        return flag;
    }
    /*发送消息通用方法*/

    public  void send(Long  useId,String identity,String  content){
        PushRequest pushRequest=new PushRequest();
        pushRequest.setUserId(useId);
        pushRequest.setIdentity(identity);
        pushRequest.setContent(content);
        serviceSsePushClient.push(pushRequest);
    }
    /*查询乘客是否下个单*/
    private boolean isExist(String key) {
        //先查询是否存在
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        if (aBoolean) {
            String s = stringRedisTemplate.opsForValue().get(key);
            int i = Integer.parseInt(s);
            if (i > 2) {
                return true;
            } else
                stringRedisTemplate.opsForValue().increment(key);
        } else
            stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 60, TimeUnit.MINUTES);
        return false;
    }
    /*查询乘客下单状态*/
    public Long isOrderGoing(Long passengerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstant.ORDER_START)
                .or().eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstant.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstant.TO_START_PAY)
                .or().eq("order_status", OrderConstant.SUCCESS_PAY));


        Long count = orderMapper.selectCount(queryWrapper);
        return count;
    }
    /*查询司机接单状态*/
    public Long isOrderGoingDriver(Long driverId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstant.PICK_UP_PASSENGER));

        Long count = orderMapper.selectCount(queryWrapper);
        log.info("司机id:" + driverId + ",正在进行的订单的数量是" + count);
        return count;
    }
    /*司机去接乘客*/
    public ResponseResult ToPickUpPassenger(OrderRequest orderRequest){
        LocalDateTime localDateTime=LocalDateTime.now();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        QueryWrapper<OrderInfo> orderInfo = queryWrapper.eq("id", orderRequest.getId());
        OrderInfo info = orderMapper.selectOne(orderInfo);
        info.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        info.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        info.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        info.setToPickUpPassengerTime(localDateTime);
        info.setOrderStatus(OrderConstant.DRIVER_TO_PICK_UP_PASSENGER);
        orderMapper.updateById(info);
        return ResponseResult.success();
    }

    /*司机到达乘客上车点*/
    public ResponseResult ArrivedDeparture(OrderRequest orderRequest){
        String id = orderRequest.getId();
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.DRIVER_ARRIVED_DEPARTURE);
        orderMapper.updateById(orderInfo);
        return ResponseResult.success();
    }
    /*司机接到乘客*/
    public ResponseResult PickUpPassenger(OrderRequest orderRequest){
        String id = orderRequest.getId();
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.PICK_UP_PASSENGER);
        orderMapper.updateById(orderInfo);
        return ResponseResult.success();
    }
    /*乘客下车*/
    public ResponseResult PassengerGetOff(OrderRequest orderRequest){
        String id = orderRequest.getId();
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());
        orderInfo.setPassengerGetoffLongitude(orderInfo.getPickUpPassengerLongitude());
        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        //司机行驶路程和时间
        ResponseResult<Car> car = serviceDriverUserClient.getCar(orderInfo.getCarId());
        Long startTime=orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long endTime=LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        ResponseResult<TrsearchResponse> result= serviceMapClient.trsearch(car.getData().getTid(),startTime,endTime);
        orderInfo.setDriveTime(result.getData().getTime());
        orderInfo.setDriveMile(result.getData().getDistance());
        orderInfo.setOrderStatus(OrderConstant.PASSENGER_GETOFF);
        orderMapper.updateById(orderInfo);
        return ResponseResult.success();
    }
    /*转化单位*/


}
