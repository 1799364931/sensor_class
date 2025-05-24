package com.example.sensor.repository;

import com.example.sensor.pojo.LogData;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository //spring bean
public interface LogDataRepository extends CrudRepository<LogData,Integer> {
    // 自定义查询方法
    @Override
    @NonNull
    Optional<LogData> findById(@NonNull Integer logId);


    ArrayList<LogData> findByLogTimeBetweenAndTemperatureBetweenAndHumidityBetweenAndAlert(
            LocalDateTime start, LocalDateTime end, Double temperatureMin, Double temperatureMax, Double humidityMin, Double humidityMax,boolean isAlert
    );

    ArrayList<LogData> findByLogTimeBetweenAndTemperatureBetweenAndHumidityBetween(
            LocalDateTime start, LocalDateTime end, Double temperatureMin, Double temperatureMax, Double humidityMin, Double humidityMax
    );

    //计算平均温度
    @Query("SELECT AVG(temperature) FROM LogData WHERE logId IN (:ids)")
    Double findAverageTemperature(@Param("ids") List<Integer> ids);

    //计算平均湿度
    @Query("SELECT AVG(humidity) FROM LogData WHERE logId IN (:ids)")
    Double findAverageHumidity(@Param("ids") List<Integer> ids);

    //计算最大温度
    @Query("SELECT MAX(temperature) FROM LogData WHERE logId IN (:ids)")
    Double findMaxTemperature(@Param("ids") List<Integer> ids);

    //计算最小温度
    @Query("SELECT MIN(temperature) FROM LogData WHERE logId IN (:ids)")
    Double findMinTemperature(@Param("ids") List<Integer> ids);

    //计算最大湿度
    @Query("SELECT MAX(humidity) FROM LogData WHERE logId IN (:ids)")
    Double findMaxHumidity(@Param("ids") List<Integer> ids);

    //计算最小湿度
    @Query("SELECT MIN(humidity) FROM LogData WHERE logId IN (:ids)")
    Double findMinHumidity(@Param("ids") List<Integer> ids);

    //计算告警次数
    @Query("SELECT COUNT(alert) FROM LogData WHERE logId IN (:ids) AND alert = true")
    Integer findAlertCount(@Param("ids") List<Integer> ids);
}
