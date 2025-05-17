package com.example.sensor.service;

import com.example.sensor.pojo.LogAlertManager;
import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.dto.LogDataDto;
import com.example.sensor.repository.LogDataRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service //spring bean
public class LogDataService implements ILogDataService {
    @Autowired
    LogDataRepository logDataRepository;

    @Override
    public LogData add(LogDataDto logDataDto){
        var logDataPojo = new LogData();
        BeanUtils.copyProperties(logDataDto,logDataPojo);
        return logDataRepository.save(logDataPojo);
    }

    @Override
    public LogData query(Integer logId){
        var logDataPojo = logDataRepository.findById(logId);
        return logDataPojo.orElse(null);
    }

    @Override
    public LogData update(Integer logId,LogDataDto logDataDto){
        return logDataRepository.findById(logId).map(
                existingLogData->{
                    existingLogData.setLogTime(logDataDto.getLogTime());
                    existingLogData.setHumidity(logDataDto.getHumidity());
                    existingLogData.setTemperature(logDataDto.getTemperature());
                    return logDataRepository.save(existingLogData);
                }
        ).orElseThrow(() -> new RuntimeException("用户未找到"));
    }

    @Override
    public LogData delete(Integer logId){
        if(logDataRepository.existsById(logId)){
            var logData = logDataRepository.findById(logId).isPresent()?new LogData(logDataRepository.findById(logId).get()):null;
            logDataRepository.deleteById(logId);
            return logData;
        }
        return null;
    }






}
