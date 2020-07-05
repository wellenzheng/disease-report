package com.example.diseasereport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.diseasereport.mapper.DoctorMapper;
import com.example.diseasereport.mapper.StatisticMapper;
import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.mapper.UserMapper;
import com.example.diseasereport.model.User;
import com.example.diseasereport.model.UserInfo;

import net.bytebuddy.asm.Advice.Unused;


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

    @Test
    void contextLoads() {
        System.out.println(userInfoMapper.insertOrUpdate(UserInfo.builder().userId(2).schoolId("123").build()));
    }

}
