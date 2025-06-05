package com.example.sensor.service;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogFilter;
import com.example.sensor.pojo.dto.IdListDto;
import com.example.sensor.pojo.dto.LogFilterDto;
import com.example.sensor.repository.LogDataRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogDataBatchService {

    private final LogDataRepository logDataRepository;
    @Autowired
    public LogDataBatchService(LogDataRepository logDataRepository) {
        this.logDataRepository = logDataRepository;
    }

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
            return switch (logFilter.getIsAlert()) {
                case ALERT -> logDataRepository.findByLogTimeBetweenAndTemperatureBetweenAndHumidityBetweenAndAlert(
                        logFilter.getStartDate(), logFilter.getEndDate(),
                        logFilter.getTemperatureMin(), logFilter.getTemperatureMax(),
                        logFilter.getHumidityMin(), logFilter.getHumidityMax(),
                        true
                );
                case NOT_ALERT -> logDataRepository.findByLogTimeBetweenAndTemperatureBetweenAndHumidityBetweenAndAlert(
                        logFilter.getStartDate(), logFilter.getEndDate(),
                        logFilter.getTemperatureMin(), logFilter.getTemperatureMax(),
                        logFilter.getHumidityMin(), logFilter.getHumidityMax(),
                        false
                );
                case ALL -> logDataRepository.findByLogTimeBetweenAndTemperatureBetweenAndHumidityBetween(
                        logFilter.getStartDate(), logFilter.getEndDate(),
                        logFilter.getTemperatureMin(), logFilter.getTemperatureMax(),
                        logFilter.getHumidityMin(), logFilter.getHumidityMax()
                );
            };
        }
    }

    public ArrayList<LogData> deleteBatchLog(IdListDto idListDto){
        ArrayList<LogData> logDataArrayList = new ArrayList<>();
        for(var logId:idListDto.getIds()){
            var logData = logDataRepository.findById(logId);
            if(logData.isPresent()){
                logDataArrayList.add(new LogData(logData.get()));
                logDataRepository.deleteById(logId);
            }
        }
        return logDataArrayList;
    }

    public ArrayList<LogData> fetchBatchLogById(IdListDto idListDto) {
        ArrayList<LogData> logDataArrayList = new ArrayList<>();
        for (var logId : idListDto.getIds()) {
            var logData = logDataRepository.findById(logId);
            logData.ifPresent(logDataArrayList::add);
        }
        return logDataArrayList;
    }
}
