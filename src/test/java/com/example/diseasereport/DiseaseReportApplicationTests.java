package com.example.diseasereport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.diseasereport.mapper.DoctorMapper;
import com.example.diseasereport.mapper.StatisticMapper;
import com.example.diseasereport.mapper.UserMapper;
import com.example.diseasereport.model.User;

import net.bytebuddy.asm.Advice.Unused;


@SpringBootTest
class DiseaseReportApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    StatisticMapper statisticMapper;
    @Autowired
    DoctorMapper doctorMapper;

    @Test
    void contextLoads() {
        System.out.println(userMapper.insert(User.builder()
                .id(11)
                .email("123@QQ.com")
                .build()));
    }

}
