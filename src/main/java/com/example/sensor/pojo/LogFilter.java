package com.example.sensor.pojo;


import com.example.sensor.pojo.dto.LogFilterDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

// 用于进行过滤
public class LogFilter {
    //日期过滤
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public LogFilter(LocalDateTime startDate,LocalDateTime endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LogFilter(LogFilterDto logFilterDto){
        this.startDate = logFilterDto.getStartDate();
        this.endDate = logFilterDto.getEndDate();
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
