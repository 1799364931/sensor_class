package com.example.sensor.pojo.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
public class LogAlertMannagerDto {

    private Timestamp setTime;
    private Double temperatureMax;
    private Double temperatureMin;
    private Double humidityMax;
    private Double humidityMin;

    public Timestamp getSetTime() {
        return setTime;
    }

    public void setSetTime(Timestamp setTime) {
        this.setTime = setTime;
    }

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
}
