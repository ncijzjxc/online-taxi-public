package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.image.Kernel;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 19:12
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
public class ServiceClient {
    @Value("${amp.api.key}")
    private String key;
    @Autowired
    RestTemplate restTemplate;

    public ResponseResult serviceFromMap(String name){
        StringBuilder url=new StringBuilder();
        url.append(AmpConfigConstant.SERVICE_ADD_URL);
        url.append("?");
        url.append("key="+key);
        url.append("&");
        url.append("name="+name);


        ResponseEntity<String> entity = restTemplate.postForEntity(url.toString(), null, String.class);
        JSONObject jsonObject = JSONObject.fromObject(entity.getBody());
        JSONObject data = jsonObject.getJSONObject("data");
        String sid = data.getString("sid");
        return ResponseResult.success(sid);
    }

}
