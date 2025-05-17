package com.example.sensor.service;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogFilter;
import com.example.sensor.pojo.dto.LogDataDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface ILogDataService {


    //基础的增删查改
    LogData add(LogDataDto logDataDto);

    LogData query(Integer logId);

    LogData update(Integer logId,LogDataDto logDataDto);

    LogData delete(Integer logId);



}
