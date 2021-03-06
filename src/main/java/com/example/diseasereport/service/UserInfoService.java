package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.response.InfoAndHealth;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@Slf4j
@Service
public class UserInfoService {

    private final String prefix = "userInfo:";

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    public Integer insertOrUpdate(UserInfo userInfo) {
        int integer = userInfoMapper.insertOrUpdate(userInfo);
        userInfoMapper.updateAutoIncr();
        if (integer != 0) {
            InfoAndHealth infoAndHealth = userInfoMapper.selectInfoAndHealth(userInfo.getUserId());
            redisUtils.set(prefix + userInfo.getUserId(), userInfo);
            if (redisUtils.hasKey("infoAndHealth")) {
                redisUtils.lUpdateIndex("infoAndHealth", userInfo.getUserId() - 2, infoAndHealth);
            }
        }
        return integer;
    }

    public UserInfo getByUserId(Integer userId) {
        UserInfo userInfo = (UserInfo) redisUtils.get(prefix + userId);
        if (userInfo != null) {
            return userInfo;
        }
        UserInfo info = userInfoMapper.selectByUserId(userId);
        if (info != null) {
            redisUtils.set(prefix + info.getUserId(), info);
        }
        return info;
    }
}
