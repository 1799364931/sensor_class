package com.example.sensor.service;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogFilter;
import com.example.sensor.pojo.dto.LogFilterDto;
import com.example.sensor.repository.LogDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogDataBatchService implements ILogDataBatchService{

    @Autowired
    LogDataRepository logDataRepository;

    @Override
    public ArrayList<LogData> fetchBatchLog(LogFilterDto logFilterDto){
        var logFilter = logFilterDto ==null? null : new LogFilter(logFilterDto);
        //如果没有过滤器 批量发送所有数据
        if(logFilter == null){
            ArrayList<LogData> logDatas = new ArrayList<LogData>();
            for (LogData logData : logDataRepository.findAll()) {
                logDatas.add(logData);
            }
            return logDatas;
        }
        //如果有过滤器
        else{
            return logDataRepository.findBylogTimeBetween(logFilter.getStartDate(),logFilter.getEndDate());
        }
    }
}
