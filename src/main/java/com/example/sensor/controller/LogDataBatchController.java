package com.example.sensor.controller;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogFilter;
import com.example.sensor.pojo.dto.LogFilterDto;
import com.example.sensor.service.ILogDataBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping("/log_batch")
public class LogDataBatchController {
//http = localhost:8888/log_batch
    @Autowired
    ILogDataBatchService iLogDataBatchService;

    //获取批量数据
    @GetMapping
    public ArrayList<LogData> getBatchLogData(@RequestBody LogFilterDto logFilterDto){
        return iLogDataBatchService.fetchBatchLog(logFilterDto);
    }
}
