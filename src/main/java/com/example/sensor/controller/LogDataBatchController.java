package com.example.sensor.controller;

import com.example.sensor.pojo.*;
import com.example.sensor.pojo.dto.LogFilterDto;
import com.example.sensor.pojo.dto.StaticIdListDto;
import com.example.sensor.service.ILogDataBatchService;
import com.example.sensor.service.LogDataStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/log_batch")
public class LogDataBatchController {
//http = localhost:8080/log_batch
    @Autowired
    ILogDataBatchService iLogDataBatchService;

    @Autowired
    LogDataStatisticService logDataStatisticService;

    //获取批量数据
    @PostMapping
    public ResponseMessage<ArrayList<LogData>> getBatchLogData(@RequestBody LogFilterDto logFilterDto){
        var res = iLogDataBatchService.fetchBatchLog(logFilterDto);
        if(res.isEmpty()){
            return ResponseMessage.NoContent(null);
        }
        else{
            return ResponseMessage.Success(res);
        }
    }

    //统计日志信息
    @PostMapping("statistic")
    public ResponseMessage<LogDataStaticData> statisticLogData(@RequestBody StaticIdListDto staticIdListDto){
        LogDataStaticData logDataStaticData = logDataStatisticService.statisticLogData(staticIdListDto);
        if (logDataStaticData == null) {
            return ResponseMessage.NoContent(null);
        } else {
            return ResponseMessage.Success(logDataStaticData);
        }
    }

}
