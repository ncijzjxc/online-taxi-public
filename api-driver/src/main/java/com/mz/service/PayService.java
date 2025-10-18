package com.mz.service;

import com.mz.constant.DriverCarConstant;
import com.mz.constant.IdentityConstant;
import com.mz.dto.ResponseResult;
import com.mz.remote.ServiceSsePushClient;
import com.mz.request.PushRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/10/19 - 10 - 19 - 19:40
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class PayService {
    @Autowired
    ServiceSsePushClient serviceSsePushClient;
    public ResponseResult PayPrice(String orderId,String price,String passengerId){
        JSONObject message=new JSONObject();
        message.put("price",price);
        message.put("orderId",orderId);
        PushRequest pushRequest=new PushRequest();
        pushRequest.setUserId(Long.valueOf(passengerId));
        pushRequest.setIdentity(IdentityConstant.PASSENGER_IDENTITY);
        pushRequest.setContent(message.toString());
        serviceSsePushClient.push(pushRequest);
        return ResponseResult.success("推送成功");
    }
}
