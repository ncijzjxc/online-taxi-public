package com.mz.remote;

import com.mz.request.PushRequest;
import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/9/10 - 09 - 10 - 23:46
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient(value = "service-sse-push",path = "/service-sse-push")
public interface ServiceSsePushClient {


    @RequestMapping(method = RequestMethod.POST,value = "/push")
    public String push(@RequestBody PushRequest pushRequest);

}
