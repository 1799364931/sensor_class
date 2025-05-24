package com.example.sensor.controller;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.ResponseMessage;
import com.example.sensor.pojo.dto.LogDataDto;
import com.example.sensor.service.ILogDataService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //允许接口方法返回对象，对象转化为jason文本
@RequestMapping("/log") //访问的路由 localhost:8080/log/
public class LogDataController {
    @Autowired
    ILogDataService logDataService;
    //add

    @PostMapping
    public ResponseMessage<LogData> add(@RequestBody LogDataDto logDataDto){
        var logdata = logDataService.add(logDataDto);
        return ResponseMessage.Success(logdata);
    }

    //query
    @GetMapping("/{logId}")
    public ResponseMessage<LogData> query(@PathVariable Integer logId){
        System.out.println("some in:"+logId);
        var logData = logDataService.query(logId);
        if(logData == null){
            return ResponseMessage.NoContent(null);
        }
        return ResponseMessage.Success(logData);
    }

    //modify
    @PutMapping("/{logId}")
    public ResponseMessage<LogData> update(@PathVariable Integer logId,@RequestBody LogDataDto logDataDto){
        var logData = logDataService.update(logId,logDataDto);
        return ResponseMessage.Success(logData);
    }

    //delete
    @DeleteMapping("/{logId}")
    public ResponseMessage<LogData> delete(@PathVariable Integer logId){
        var logData = logDataService.delete(logId);
        return logData == null ? ResponseMessage.NoContent(null) : ResponseMessage.Success(logData);
    }

}
