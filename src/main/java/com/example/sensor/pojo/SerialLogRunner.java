package com.example.sensor.pojo;

import com.example.sensor.service.SerialLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SerialLogRunner implements CommandLineRunner {

    private final SerialLogService serialLogService;

    @Autowired
    public SerialLogRunner(SerialLogService serialLogService) {
        this.serialLogService = serialLogService;
    }

    @Override
    public void run(String... args) {
        serialLogService.startListening(); // 启动串口监听
    }
}
