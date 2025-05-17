package com.example.sensor.controller;


import com.example.sensor.pojo.LogAlertManager;
import com.example.sensor.pojo.dto.LogAlertManagerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log_alert")
public class LogAlertManagerController {
    @Autowired
    private LogAlertManager logAlertManager;

    //获取当前的告警设置
    @GetMapping("")
    public LogAlertManager getLogAlertMannager(){
        return logAlertManager;
    }

    //设置当前的告警系统
    @PostMapping
    public LogAlertManager setLogAlertMannager(@RequestBody LogAlertManagerDto logAlertManagerDto){
        logAlertManager.setLogAlertManager(logAlertManagerDto);
        return logAlertManager;
    }




}
