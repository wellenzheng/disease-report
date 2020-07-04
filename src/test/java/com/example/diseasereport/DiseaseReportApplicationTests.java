package com.example.diseasereport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.diseasereport.mapper.StatisticMapper;


@SpringBootTest
class DiseaseReportApplicationTests {

    @Autowired
    StatisticMapper statisticMapper;

    @Test
    void contextLoads() {
        System.out.println(statisticMapper.selectGroupByDate());
    }

}
