package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.HealthChartMapper;
import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.model.HealthChart;
import com.example.diseasereport.response.InfoAndHealth;
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
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    public Integer addHealthChart(HealthChart healthChart) {
        int i = healthChartMapper.insert(healthChart);
        if (i != 0) {
            redisUtils.set(prefix + healthChart.getUserId(), healthChart);
            InfoAndHealth infoAndHealth = userInfoMapper.selectInfoAndHealth(healthChart.getUserId());
            if (redisUtils.hasKey("infoAndHealth")) {
                redisUtils.lUpdateIndex("infoAndHealth", healthChart.getUserId(), infoAndHealth);
            }
        }
        healthChartMapper.updateAutoIncr();
        return i;
    }

    public HealthChart getByUserId(Integer userId) {
        HealthChart healthChart = (HealthChart) redisUtils.get(prefix + userId);
        if (healthChart != null) {
            return healthChart;
        }
        HealthChart chart = healthChartMapper.selectByUserId(userId);
        if (chart != null) {
            redisUtils.set(prefix + chart.getUserId(), chart);
        }
        return chart;
    }
}
