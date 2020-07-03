package com.example.diseasereport.mapper;

import com.example.diseasereport.model.UserInfo;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserInfoMapper {
    int deleteById(Integer id);

    int insert(UserInfo userInfo);

    UserInfo selectById(Integer id);

    List<UserInfo> selectAll();

    int updateById(UserInfo userInfo);

    Integer updateByUserId(UserInfo userInfo);
}