package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.DoctorMapper;
import com.example.diseasereport.model.Doctor;

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

    public Doctor getByUserId(Integer userId) {
        return doctorMapper.selectByUserId(userId);
    }
}
