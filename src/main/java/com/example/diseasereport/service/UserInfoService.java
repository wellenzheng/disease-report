package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.model.UserInfo;
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

    public Integer updateByUserId(UserInfo userInfo) {
        if (redisUtils.set(prefix + userInfo.getUserId(), userInfo)) {
            return userInfoMapper.updateByUserId(userInfo);
        }
        return 0;
    }

    public UserInfo getByUserId(Integer userId) {
        UserInfo userInfo = (UserInfo) redisUtils.get(prefix + userId);
        if (userInfo != null) {
            return userInfo;
        }
        UserInfo info = userInfoMapper.selectByUserId(userId);
        redisUtils.set(prefix + info.getUserId(), info);
        return info;
    }
}
