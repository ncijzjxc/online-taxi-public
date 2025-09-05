package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.dto.PointDto;
import com.mz.dto.ResponseResult;
import com.mz.response.PointResponse;
import net.sf.json.JSONObject;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @Auther: mz
 * @Date: 2025/9/4 - 09 - 04 - 15:14
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
public class PointClient {
    @Value("${amp.api.key}")
    private String key;
    @Value("${amp.api.sid}")
    private  String sid;
    @Autowired
    RestTemplate restTemplate;
    //
    public ResponseResult<PointDto> upload(PointResponse pointResponse){
        StringBuilder url=new StringBuilder();
        url.append(AmpConfigConstant.POINT_UPLOAD_URL);
        url.append("?");
        url.append("key="+key);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("tid="+pointResponse.getTid());
        url.append("&");
        url.append("trid="+pointResponse.getTrid());
        url.append("&");
        url.append("points=");
        url.append("%5B");
        PointDto[] points = pointResponse.getPoints();
        for (PointDto p : points
        ) {
            url.append("%7B");
            String locatetime = p.getLocatetime();
            String location = p.getLocation();
            url.append("%22location%22");
            url.append("%3A");
            url.append("%22"+location+"%22");
            url.append("%2C");

            url.append("%22locatetime%22");
            url.append("%3A");
            url.append(locatetime);

            url.append("%7D");
        }
        url.append("%5D");
        System.out.println(url.toString());
        ResponseEntity<String> entity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
        JSONObject jsonObject = JSONObject.fromObject(entity.getBody());
        JSONObject data = jsonObject.getJSONObject("data");
        System.out.println(data);
        return ResponseResult.success();
    }
}
