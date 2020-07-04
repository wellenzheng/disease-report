package com.example.diseasereport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.User;


@Repository
@Mapper
public interface UserMapper {
    int deleteById(Integer id);

    int insert(@Param("user") User user);

    User selectById(Integer id);

    List<User> selectAll();

    int updateById(@Param("user") User user);

    User selectByEmail(@Param("email") String email);
}