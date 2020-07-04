package com.example.diseasereport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.UserInfoMapper;
import com.example.diseasereport.mapper.UserMapper;
import com.example.diseasereport.model.User;
import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.request.UserRequest;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-01
 */

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    public Integer register(UserRequest userRequest) {
        if (userRequest == null || userMapper.selectByEmail(userRequest.getEmail()) != null) {
            return -1; //表示用户已存在
        }
        if (verify(userRequest)) {
            int userId = userMapper.insert(User.builder()
                    .email(userRequest.getEmail())
                    .password(passwordEncoder().encode(userRequest.getPassword()))
                    .role("STUDENT")
                    .build());
            if (redisUtils.set("userInfo" + userId, UserInfo.builder().userId(userId).build())) {
                userInfoMapper.insert(UserInfo.builder().userId(userId).build());
            }
            return userId;
        }
        return 0; //表示验证码不正确
    }

    public Boolean verify(UserRequest userRequest) {
        String code = (String) redisUtils.get(userRequest.getEmail());
        if (code != null && code.equals(userRequest.getCode())) {
            redisUtils.delete(code);
            return true;
        }
        return false;
    }
}
