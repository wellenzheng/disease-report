package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@Slf4j
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public Integer updateByUserId(UserInfo userInfo) {
        return userInfoMapper.updateByUserId(userInfo);
    }

}
