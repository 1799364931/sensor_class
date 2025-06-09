package com.example.sensor.controller;

import com.example.sensor.pojo.*;
import com.example.sensor.pojo.dto.IdListDto;
import com.example.sensor.pojo.dto.LogFilterDto;
import com.example.sensor.service.LogDataBatchService;
import com.example.sensor.service.LogDataStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/log_batch")
@Tag(name = "日志批量处理", description = "日志批量处理相关接口")
public class LogDataBatchController {
    private final LogDataBatchService logDataBatchService;
    private final LogDataStatisticService logDataStatisticService;

    @Autowired
    public LogDataBatchController(LogDataBatchService logDataBatchService, LogDataStatisticService logDataStatisticService) {
        this.logDataBatchService = logDataBatchService;

         this.logDataStatisticService = logDataStatisticService;
    }
    //获取批量数据
    @PostMapping
    @Operation(summary = "获取批量日志数据", description = "根据提供的过滤条件获取批量日志数据，需提供LogFilterDto对象")
    public ResponseMessage<ArrayList<LogData>> getBatchLogData(@RequestBody LogFilterDto logFilterDto){
        var res = logDataBatchService.fetchBatchLog(logFilterDto);
        if(res.isEmpty()){
            return ResponseMessage.NoContent(null);
        }
        else{
            return ResponseMessage.Success(res);
        }
    }

    @PostMapping("by-id")
    @Operation(summary = "按照ID列表获取日志数据", description = "根据提供的ID列表获取日志数据，需提供IdListDto对象")
    public ResponseMessage<ArrayList<LogData>> getBatchLogDataById(@RequestBody IdListDto idListDto){
        ArrayList<LogData> logDataArrayList = logDataBatchService.fetchBatchLogById(idListDto);
        return logDataArrayList.isEmpty() ?
            ResponseMessage.NoContent(null) :
            ResponseMessage.Success(logDataArrayList);
    }

    //统计日志信息
    @PostMapping("statistic")
    @Operation(summary = "统计日志数据", description = "根据提供的ID列表统计日志数据，需提供IdListDto对象")
    public ResponseMessage<LogDataStaticData> statisticLogData(@RequestBody IdListDto idListDto){
        LogDataStaticData logDataStaticData = logDataStatisticService.statisticLogData(idListDto);
        if (logDataStaticData == null) {
            return ResponseMessage.NoContent(null);
        } else {
            return ResponseMessage.Success(logDataStaticData);
        }
    }

    //删除日志
    @DeleteMapping
    @Operation(summary = "批量删除日志数据", description = "根据提供的ID列表批量删除日志数据，需提供IdListDto对象")
    public ResponseMessage<ArrayList<LogData>> deleteBatchLogData(@RequestBody IdListDto idListDto){
        ArrayList<LogData> logDataArrayList = logDataBatchService.deleteBatchLog(idListDto);
        if(logDataArrayList.isEmpty()){
            return ResponseMessage.NoContent(null);
        } else {
            return ResponseMessage.Success(logDataArrayList);
        }
    }

}
