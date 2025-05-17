package com.example.sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.example.sensor.pojo")
@SpringBootApplication
public class SensorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SensorApplication.class, args);
    }

}
