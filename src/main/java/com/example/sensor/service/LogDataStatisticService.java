package com.example.sensor.service;

import com.example.sensor.pojo.LogData;
import com.example.sensor.pojo.LogDataStaticData;
import com.example.sensor.pojo.dto.StaticIdListDto;
import com.example.sensor.repository.LogDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogDataStatisticService {
    // 统计日志信息
    // 这里可以实现统计逻辑，比如计算平均温度、湿度等
    // 需要根据传入的 StaticIdListDto 获取对应的日志数据进行统计

    @Autowired
    LogDataRepository logDataRepository;

    public LogDataStaticData statisticLogData(StaticIdListDto staticIdListDto) {
        LogDataStaticData logDataStaticData = new LogDataStaticData();

        // 假设我们有一个方法 fetchLogDataByIds 来获取日志数据
        List<LogData> logDataList = new ArrayList<>();
        for(Integer id : staticIdListDto.getIds()) {
            logDataRepository.findById(id).ifPresent(logDataList::add);
        }

        if (logDataList.isEmpty()) {
            return null; // 如果没有数据，返回空的统计数据
        }

        // 计算平均湿度
        logDataStaticData.setAverageHumidity(
                logDataRepository.findAverageHumidity(staticIdListDto.getIds())
        );
        // 计算平均温度
        logDataStaticData.setAverageTemperature(
                logDataRepository.findAverageTemperature(staticIdListDto.getIds())
        );
        // 计算最大温度
        logDataStaticData.setMaxTemperature(
                logDataRepository.findMaxTemperature(staticIdListDto.getIds())
        );
        // 计算最小温度
        logDataStaticData.setMinTemperature(
                logDataRepository.findMinTemperature(staticIdListDto.getIds())
        );
        // 计算最大湿度
        logDataStaticData.setMaxHumidity(
                logDataRepository.findMaxHumidity(staticIdListDto.getIds())
        );
        // 计算最小湿度
        logDataStaticData.setMinHumidity(
                logDataRepository.findMinHumidity(staticIdListDto.getIds())
        );
        // 计算告警数量
        logDataStaticData.setAlertCount(
                logDataRepository.findAlertCount(staticIdListDto.getIds())
        );

        return logDataStaticData;
    }
}
