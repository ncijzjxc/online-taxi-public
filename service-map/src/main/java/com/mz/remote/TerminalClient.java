package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.response.TrsearchResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 22:55
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
public class TerminalClient {
    @Value("${amp.api.key}")
    private String key;
    @Value("${amp.api.sid}")
    private String sid;
    @Autowired
    RestTemplate restTemplate;


    public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmpConfigConstant.TERMINAL_ADD_URL);
        url.append("?");
        url.append("key=" + key);
        url.append("&");
        url.append("sid=" + sid);
        url.append("&");
        url.append("name=" + name);
        url.append("&");
        url.append("desc=" + desc);
        System.out.println("创建终端请求：" + url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("创建终端响应：" + stringResponseEntity.getBody());
        /**
         * {
         *     "data": {
         *         "name": "车辆2",
         *         "tid": 583145283,
         *         "sid": 797498
         *     },
         *     "errcode": 10000,
         *     "errdetail": null,
         *     "errmsg": "OK"
         * }
         */
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<TerminalResponse> aroundsearch(String center, Integer radius) {
        StringBuilder url = new StringBuilder();
        url.append(AmpConfigConstant.TERMINAL_AROUND_SEARCH);
        url.append("?");
        url.append("key=" + key);
        url.append("&");
        url.append("sid=" + sid);
        url.append("&");
        url.append("center=" + center);
        url.append("&");
        url.append("radius=" + radius);

        System.out.println("终端搜索请求：" + url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("终端搜索响应：" + stringResponseEntity.getBody());

        // 解析终端搜索结果
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");

        List<TerminalResponse> terminalResponseList = new ArrayList<>();

        JSONArray results = data.getJSONArray("results");
        for (int i = 0; i < results.size(); i++) {
            TerminalResponse terminalResponse = new TerminalResponse();

            JSONObject jsonObject = results.getJSONObject(i);
            // desc是carId，
            String desc = jsonObject.getString("desc");
            Long carId = 1l;
            if (!desc.isEmpty()) {
                carId = Long.parseLong(desc);
            }
            String tid = jsonObject.getString("tid");

            JSONObject location = jsonObject.getJSONObject("location");
            String longitude = location.getString("longitude");
            String latitude = location.getString("latitude");

            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid);
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);

            terminalResponseList.add(terminalResponse);
        }


        return ResponseResult.success(terminalResponseList);
    }

    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime, Long endtime) {
        StringBuilder url = new StringBuilder();
        url.append(AmpConfigConstant.TERMINAL_TRSEARCH);
        url.append("?");
        url.append("key="+key);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("tid="+tid);
        url.append("&");
        url.append("starttime="+starttime);
        url.append("&");
        url.append("endtime="+endtime);
        ResponseEntity<String> entity=restTemplate.postForEntity(url.toString(),null,String.class);
        //解析结果;
        JSONObject jsonObject=JSONObject.fromObject(entity.getBody());
        JSONObject data = jsonObject.getJSONObject("data");
        int count = data.getInt("counts");
        if(count==0){
            return null;
        }

        JSONArray array = data.getJSONArray("tracks");
        Long driverMile=0l;
        Long driverTime=0l;
        for(int i=0;i<array.size();i++){
            JSONObject track = array.getJSONObject(i);
            Long distance = track.getLong("distance");
            driverMile+=distance;
            Long time = track.getLong("time");
            time=time/(1000*60);
            driverTime+=time;

        }
        TrsearchResponse trsearchResponse=new TrsearchResponse();
        trsearchResponse.setTime(driverTime);
        trsearchResponse.setDistance(driverMile);
        return ResponseResult.success(trsearchResponse);
    }
}
