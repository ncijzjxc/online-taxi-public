package com.mz.service;

import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.remote.TerminalClient;
import com.mz.response.TrsearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 23:04
 * @Description: com.mz.service
 * @version: 6.0
 */
@Service
public class TerminalService {
    @Autowired
    TerminalClient terminalClient;
    public ResponseResult addTerminal(String name,String desc){
        return terminalClient.addTerminal(name ,desc);
    }

    public ResponseResult<TerminalResponse> aroundSearch(String center, Integer radius){
        return terminalClient.aroundsearch(center,radius);
    }

    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime, Long endtime){
        return terminalClient.trsearch(tid,starttime,endtime);
    }
}
