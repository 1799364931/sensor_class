package com.example.sensor.service;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogFilter;
import com.example.sensor.pojo.dto.IdListDto;
import com.example.sensor.pojo.dto.LogFilterDto;

import java.util.ArrayList;

public interface ILogDataBatchService {

    //批量的获取
    ArrayList<LogData> fetchBatchLog(LogFilterDto logFilterDto);

    //批量删除
    ArrayList<LogData> deleteBatchLog(IdListDto idListDto);
}
