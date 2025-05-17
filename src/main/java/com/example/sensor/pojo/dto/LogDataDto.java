package com.example.sensor.pojo.dto;

import jakarta.persistence.Column;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class LogDataDto {
    private Integer logId;
    private Timestamp logTime;
    private Double temperature;
    private Double humidity;
    private boolean alert;


    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
