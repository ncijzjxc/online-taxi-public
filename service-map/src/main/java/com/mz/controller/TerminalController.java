package com.mz.controller;

import com.mz.dto.ResponseResult;
import com.mz.dto.TerminalResponse;
import com.mz.response.TrsearchResponse;
import com.mz.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: mz
 * @Date: 2025/9/2 - 09 - 02 - 22:16
 * @Description: com.mz.controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {
    @Autowired
    TerminalService terminalService;
    @PostMapping("/add")
    public ResponseResult addTerminal(@RequestParam String name, @RequestParam(required = false) String desc){

        return terminalService.addTerminal(name,desc);
    }
    @PostMapping("/aroundSearch")
    public ResponseResult<TerminalResponse> aroundSearch(@RequestParam String center, @RequestParam Integer radius){
        return terminalService.aroundSearch(center,radius);
    }
    @PostMapping("/trsearch")
    public ResponseResult<TrsearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime){
        return terminalService.trsearch(tid,starttime,endtime);
    }
}
