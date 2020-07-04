package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.HealthChartMapper;
import com.example.diseasereport.model.HealthChart;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@Slf4j
@Service
public class HealthChartService {

    private final String prefix = "health:";

    @Autowired
    private HealthChartMapper healthChartMapper;

    @Autowired
    private RedisUtils redisUtils;

    public Integer insert(HealthChart healthChart) {
        redisUtils.set(prefix + healthChart.getUserId(), healthChart);
        return healthChartMapper.insert(healthChart);
    }

    public HealthChart getByUserId(Integer userId) {
        HealthChart healthChart = (HealthChart) redisUtils.get(prefix + userId);
        if (healthChart != null) {
            return healthChart;
        }
        HealthChart chart = healthChartMapper.selectByUserId(userId);
        redisUtils.set(prefix + chart.getUserId(), chart);
        return chart;
    }
}
