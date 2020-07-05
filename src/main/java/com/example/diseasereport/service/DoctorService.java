package com.example.diseasereport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.DoctorMapper;
import com.example.diseasereport.model.Doctor;
import com.example.diseasereport.response.InfoAndHealth;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-04
 */

@Slf4j
@Service
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private RedisUtils redisUtils;

    public Doctor getByUserId(Integer userId) {
        return doctorMapper.selectByUserId(userId);
    }

    public List<InfoAndHealth> getAllInfoAndHealth() {
        List<Object> infoAndHealth = redisUtils.lGet("infoAndHealth", 0, -1);
        if (infoAndHealth != null && infoAndHealth.size() != 0) {
            return RedisUtils.castList(infoAndHealth, InfoAndHealth.class);
        }
        List<InfoAndHealth> infoAndHealthList = doctorMapper.selectAllInfoAndHealth();
        if (infoAndHealthList != null && infoAndHealthList.size() != 0) {
            redisUtils.delete("infoAndHealth");
            redisUtils.lSet("infoAndHealth", infoAndHealthList);
        }
        return infoAndHealthList;
    }
}
