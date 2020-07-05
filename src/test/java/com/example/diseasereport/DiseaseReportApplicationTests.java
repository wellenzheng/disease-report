package com.example.diseasereport;

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
import com.example.diseasereport.service.DoctorService;
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

    @Test
    void contextLoads() {
        System.out.println(userInfoMapper.insert(UserInfo.builder().userId(2).schoolId("123").build()));
    }

    @Test
    void test(){
        System.out.println(casesMapper.groupByAge());
    }

    @Test
    void redisTest() {
        //        List<Integer> list = new ArrayList<>();
        //        for (int i = 0; i < 10; i++) {
        //            list.add(i);
        //        }
        //        redisUtils.lSetAll("list1", Collections.singletonList(list));
        //        redisTemplate.opsForList().rightPushAll("list2", list.toArray());
        //        List<Object> objectList = redisUtils.lGet("list1", 0, -1);
        //        List<Object> list2 = redisTemplate.opsForList().range("list2", 0, -1);
    }

}
