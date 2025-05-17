package com.example.sensor.pojo.dto;

import java.time.LocalDateTime;

public class LogFilterDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public LogFilterDto(LocalDateTime startDate,LocalDateTime endDate){
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
