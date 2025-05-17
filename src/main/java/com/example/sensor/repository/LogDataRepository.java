package com.example.sensor.repository;

import com.example.sensor.pojo.LogData;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository //spring bean
public interface LogDataRepository extends CrudRepository<LogData,Integer> {
    // 自定义查询方法
    @Override
    @NonNull
    Optional<LogData> findById(@NonNull Integer logId);

    ArrayList<LogData> findBylogTimeBetween(LocalDateTime start,LocalDateTime end);
}
