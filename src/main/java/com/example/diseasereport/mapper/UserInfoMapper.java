package com.example.diseasereport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.response.InfoAndHealth;

@Repository
@Mapper
public interface UserInfoMapper {
    int deleteById(Integer id);

    int insert(UserInfo userInfo);

    UserInfo selectById(Integer id);

    List<UserInfo> selectAll();

    int updateById(UserInfo userInfo);

    int insertOrUpdate(UserInfo userInfo);

    UserInfo selectByUserId(@Param("userId") Integer userId);

    InfoAndHealth selectInfoAndHealth(@Param("userId") Integer userId);

    long countAll();

    void updateAutoIncr();
}