package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.ResponseResult;
import com.mz.dto.TrackResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: mz
 * @Date: 2025/9/3 - 09 - 03 - 23:29
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
@Slf4j
public class TrackClient {
    @Value("${amp.api.key}")
    private String key;
    @Value("${amp.api.sid}")
    private String sid;
    @Autowired
    RestTemplate restTemplate;

    public ResponseResult addTrack(String tid){
        StringBuilder url = new StringBuilder();
        url.append(AmpConfigConstant.TRACK_ADD_URL);
        url.append("?");
        url.append("key="+key);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("tid="+tid);
        log.info("高德地图创建轨迹请求："+url);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        log.info("高德地图创建轨迹响应："+body);
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        // 轨迹id
        String trid = data.getString("trid");
        // 轨迹名称
        String trname = "";
        if (data.has("trname")){
            trname = data.getString("trname");
        }

        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);


        return ResponseResult.success(trackResponse);
    }
}
