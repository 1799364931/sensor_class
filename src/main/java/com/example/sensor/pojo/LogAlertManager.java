package com.example.sensor.pojo;


import com.example.sensor.pojo.dto.LogAlertManagerDto;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 *
 *  用于实现告警类，调整告警的范围
 */

@Component //单例模式
@Table(name = "tb_alert")
@Entity
public class LogAlertManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_time")
    private Timestamp setTime;
    @Column(name = "temperature_max")
    private Double temperatureMax;
    @Column(name = "temperature_min")
    private Double temperatureMin;
    @Column(name = "humidity_max")
    private Double humidityMax;
    @Column(name = "humidity_min")
    private Double humidityMin;

    public LogAlertManager() {
        setTime = new Timestamp(System.currentTimeMillis());
        temperatureMax = 10D;
        temperatureMin = 0D;
        humidityMax = 100D;
        humidityMin = 0D;
    }

    private boolean isTemperatureInRange(Double temperature){
        return temperature <= this.temperatureMax && temperature >= this.temperatureMin;
    }

    private boolean isHumidityInRange(Double humidity){
        return humidity <= this.humidityMax && humidity >= this.humidityMin;
    }

    public void setLogAlertManager(LogAlertManagerDto logAlertManagerDto){
        this.temperatureMin = logAlertManagerDto.getTemperatureMin();
        this.temperatureMax = logAlertManagerDto.getTemperatureMax();
        this.humidityMax = logAlertManagerDto.getHumidityMax();
        this.humidityMin = logAlertManagerDto.getHumidityMin();
        this.setTime = logAlertManagerDto.getSetTime();
    }

    public boolean isAlert(LogData logData){
        return !(isTemperatureInRange(logData.getTemperature()) && isHumidityInRange(logData.getHumidity()));
    }

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
