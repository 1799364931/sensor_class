package com.example.sensor.pojo;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Table(name = "tb_log")
@Entity
public class LogData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;
    @Column(name = "log_time")
    private Timestamp logTime;
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "humidity")
    private Double humidity;
    @Column(name = "alert")
    private boolean alert;

    public LogData(){}

    public LogData(Timestamp logTime, Double temperature,Double humidity, boolean alert){
        this.logTime = logTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.alert = alert;
    }

    public LogData(LogData logData) {
        this.logId = logData.logId;
        this.logTime = logData.logTime;
        this.temperature = logData.temperature;
        this.humidity = logData.humidity;
        this.alert = logData.alert;
    }


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
