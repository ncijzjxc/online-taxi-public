package com.mz.remote;

import com.mz.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: mz
 * @Date: 2025/10/19 - 10 - 19 - 19:41
 * @Description: com.mz.remote
 * @version: 6.0
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {
    @RequestMapping(method = RequestMethod.POST,value = "/service-sse-push/push")
    public String push(@RequestBody PushRequest pushRequest);
}
