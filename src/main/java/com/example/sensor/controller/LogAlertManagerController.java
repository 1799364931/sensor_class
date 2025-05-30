package com.example.sensor.controller;


import com.example.sensor.pojo.LogAlertManager;
import com.example.sensor.pojo.ResponseMessage;
import com.example.sensor.pojo.dto.LogAlertManagerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/log_alert")
@Tag(name = "日志告警管理", description = "日志告警相关接口")
public class LogAlertManagerController {
    private final LogAlertManager logAlertManager;

    @Autowired
    public LogAlertManagerController(LogAlertManager logAlertManager) {
        this.logAlertManager = logAlertManager;
    }

    //获取当前的告警设置

    @GetMapping
    @Operation(summary = "获取当前日志告警设置", description = "用于获取当前的日志告警系统设置，无需鉴权")
    public ResponseMessage<LogAlertManager> getLogAlertManager(){
        return ResponseMessage.Success(logAlertManager);
    }

    //设置当前的告警系统
    @PostMapping
    @Operation(summary = "设置日志告警系统", description = "用于设置当前的日志告警系统配置，需提供LogAlertManagerDto对象")
    public ResponseMessage<LogAlertManager> setLogAlertManager(@RequestBody LogAlertManagerDto logAlertManagerDto){
        logAlertManager.setLogAlertManager(logAlertManagerDto);
        return ResponseMessage.Success(logAlertManager);
    }




}
