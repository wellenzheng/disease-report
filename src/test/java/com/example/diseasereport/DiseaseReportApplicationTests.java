package com.example.diseasereport;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.diseasereport.mapper.CasesMapper;
import com.example.diseasereport.mapper.DoctorMapper;
import com.example.diseasereport.mapper.StatisticMapper;
import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.mapper.UserMapper;
import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.response.GroupByAge;
import com.example.diseasereport.service.DoctorService;
import com.example.diseasereport.service.SynchronizeService;
import com.example.diseasereport.utils.DateFormatUtils;
import com.example.diseasereport.utils.RedisUtils;


@SpringBootTest
class DiseaseReportApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    StatisticMapper statisticMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    DoctorService doctorService;
    @Autowired
    CasesMapper casesMapper;
    @Autowired
    SynchronizeService synchronizeService;

    @Test
    void contextLoads() {
        System.out.println(userInfoMapper.insert(UserInfo.builder().userId(2).schoolId("123").build()));
    }

    @Test
    void test() {
        synchronizeService.synchronize();
    }

    @Test
    void test2() {
        List<GroupByAge> groupByAges = casesMapper.groupByAge();
        System.out.println(groupByAges);
    }

    @Test
    void redisTest() {
    }

}
