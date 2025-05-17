package com.example.sensor.repository;

import com.example.sensor.pojo.LogAlertManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAlertMannagerRepository extends CrudRepository<LogAlertManager,Integer> {
}
