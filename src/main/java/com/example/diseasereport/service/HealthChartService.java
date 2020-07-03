package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.HealthChartMapper;
import com.example.diseasereport.model.HealthChart;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@Slf4j
@Service
public class HealthChartService {

    @Autowired
    private HealthChartMapper healthChartMapper;

    public Integer insert(HealthChart healthChart) {
        return healthChartMapper.insert(healthChart);
    }
}
