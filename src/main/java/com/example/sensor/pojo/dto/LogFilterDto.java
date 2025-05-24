package com.example.sensor.pojo.dto;

import com.example.sensor.pojo.LogFilter;

import java.time.LocalDateTime;

public class LogFilterDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    //温度
    private Double temperatureMax;
    private Double temperatureMin;
    //湿度
    private Double humidityMax;
    private Double humidityMin;
    //是否告警
    private LogFilter.IsAlert isAlert;

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

    public LogFilter.IsAlert getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(LogFilter.IsAlert isAlert) {
        this.isAlert = isAlert;
    }

    public LogFilterDto(LocalDateTime startDate, LocalDateTime endDate){
        this.startDate = startDate;
        this.endDate = endDate;
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
