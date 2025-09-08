package com.mz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.dto.DriverUser;

/**
 * @Auther: mz
 * @Date: 2025/8/30 - 08 - 30 - 15:27
 * @Description: com.mz.mapper
 * @version: 6.0
 */
public interface DriverUserMapper  extends BaseMapper<DriverUser> {

    public int checkCityDriver(String cityCode);
}
