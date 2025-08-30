package com.mz.remote;

import com.mz.constant.AmpConfigConstant;
import com.mz.constant.CommonStatusEnum;
import com.mz.dto.DicDistrict;
import com.mz.dto.ResponseResult;
import com.mz.mapper.DicDistrictMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: mz
 * @Date: 2025/8/29 - 08 - 29 - 15:54
 * @Description: com.mz.remote
 * @version: 6.0
 */
@Service
public class DicDistrictClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DicDistrictMapper dicDistrictMapper;
    @Value("${amp.api.key}")
    private String key;
    public ResponseResult DicDistrict(String keywords) {
        //拼装url
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmpConfigConstant.DISTRICT_URL);
        urlBuilder.append("?keywords=");
        urlBuilder.append(keywords);
        urlBuilder.append("&");
        urlBuilder.append("subdistrict=3");
        urlBuilder.append("&");
        urlBuilder.append("key=" + key);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String body = forEntity.getBody();
        JSONObject DistricJsonObject=JSONObject.fromObject(body);
        int status = DistricJsonObject.getInt(AmpConfigConstant.STATUS);
        if(status!=1){
            return ResponseResult.fail(CommonStatusEnum.MAP_ERROR.getCode(),CommonStatusEnum.MAP_ERROR.getMessage());
        }
        JSONArray CountryJsonArray = DistricJsonObject.getJSONArray(AmpConfigConstant.DISTRICTS);
        for(int country=0;country<CountryJsonArray.size();country++){
            JSONObject CountryjsonObject = CountryJsonArray.getJSONObject(country);
            String CountryCode = CountryjsonObject.getString(AmpConfigConstant.ADDRESS_CODE);
            String CountryName= CountryjsonObject.getString(AmpConfigConstant.ADDRESS_NAME);
            String CountryParentCode="0";
            String CountryLevel= CountryjsonObject.getString(AmpConfigConstant.LEVEL);
            insertDistrict(CountryCode,CountryName,CountryParentCode,CountryLevel);
            JSONArray ProvinceJsonArray=CountryjsonObject.getJSONArray(AmpConfigConstant.DISTRICTS);

            for(int province = 0;province<ProvinceJsonArray.size();province++){
                JSONObject ProvincejsonObject = ProvinceJsonArray.getJSONObject(province);
                String ProvinceCode = ProvincejsonObject .getString(AmpConfigConstant.ADDRESS_CODE);
                String ProvinceName= ProvincejsonObject .getString(AmpConfigConstant.ADDRESS_NAME);
                String ProvinceParentCode=CountryCode;
                String ProvinceLevel= ProvincejsonObject .getString(AmpConfigConstant.LEVEL);
                insertDistrict(ProvinceCode,ProvinceName,ProvinceParentCode,ProvinceLevel);
                JSONArray CityJsonArray=ProvincejsonObject.getJSONArray(AmpConfigConstant.DISTRICTS);

                for(int city=0;city<CityJsonArray.size();city++){
                    JSONObject CityjsonObject = CityJsonArray.getJSONObject(city);
                    String CityCode = CityjsonObject .getString(AmpConfigConstant.ADDRESS_CODE);
                    String CityName= CityjsonObject .getString(AmpConfigConstant.ADDRESS_NAME);
                    String CityParentCode=ProvinceCode;
                    String CityLevel= CityjsonObject .getString(AmpConfigConstant.LEVEL);
                    insertDistrict(CityCode,CityName,CityParentCode,CityLevel);
                    JSONArray DistrictJsonArray=CityjsonObject.getJSONArray(AmpConfigConstant.DISTRICTS);

                    for(int District=0;District<DistrictJsonArray.size();District++){

                        JSONObject DistrictjsonObject = DistrictJsonArray.getJSONObject(District);
                        String DistrictCode = DistrictjsonObject .getString(AmpConfigConstant.ADDRESS_CODE);
                        String DistrictName= DistrictjsonObject .getString(AmpConfigConstant.ADDRESS_NAME);
                        String DistrictParentCode=CityCode;
                        String DistrictLevel= DistrictjsonObject .getString(AmpConfigConstant.LEVEL);
                        if(DistrictLevel.equals(AmpConfigConstant.STREET)){
                            continue;
                        }
                        insertDistrict(DistrictCode,DistrictName,DistrictParentCode,DistrictLevel);

                    }
                }
            }
        }
        return ResponseResult.success("");
    }

    public void insertDistrict(String code,String name,String parentCode,String level){
        int levelInt = generateLevel(level);
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(code);
        dicDistrict.setAddressName(name);
        dicDistrict.setParentAddressCode(parentCode);
        dicDistrict.setLevel(levelInt);
        dicDistrictMapper.insert(dicDistrict);
    }

    public int generateLevel(String level){
        int levelInt=0;
        if(level.trim().equals("country")){
            levelInt=0;
        }else if(level.trim().equals("province")){
            levelInt=1;
        }else if(level.trim().equals("city")){
            levelInt=2;
        }else if(level.trim().equals("district")){
            levelInt=3;
        }
        return levelInt;
    }
}
