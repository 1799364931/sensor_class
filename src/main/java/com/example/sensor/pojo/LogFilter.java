package com.example.sensor.pojo;


import com.example.sensor.pojo.dto.LogFilterDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

// 用于进行过滤
public class LogFilter {

    public enum IsAlert{
        ALERT,NOT_ALERT,ALL
    }
    //日期过滤
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    //温度
    private Double temperatureMax;
    private Double temperatureMin;
    //湿度
    private Double humidityMax;
    private Double humidityMin;
    //是否告警
    private IsAlert isAlert;

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getHumidityMax() {
        return humidityMax;
    }

    public void setHumidityMax(Double humidityMax) {
        this.humidityMax = humidityMax;
    }

    public Double getHumidityMin() {
        return humidityMin;
    }

    public void setHumidityMin(Double humidityMin) {
        this.humidityMin = humidityMin;
    }

    public IsAlert getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(IsAlert isAlert) {
        this.isAlert = isAlert;
    }

    public LogFilter(LocalDateTime startDate, LocalDateTime endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LogFilter(LogFilterDto logFilterDto){
        this.startDate = logFilterDto.getStartDate();
        this.endDate = logFilterDto.getEndDate();
        this.temperatureMax = logFilterDto.getTemperatureMax();
        this.temperatureMin = logFilterDto.getTemperatureMin();
        this.humidityMax = logFilterDto.getHumidityMax();
        this.humidityMin = logFilterDto.getHumidityMin();
        this.isAlert = logFilterDto.getIsAlert();

    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
