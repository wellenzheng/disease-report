package com.example.diseasereport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.Doctor;

@Repository
@Mapper
public interface DoctorMapper {
    int deleteById(Integer id);

    int insert(Doctor doctor);

    Doctor selectById(Integer id);

    List<Doctor> selectAll();

    int updateById(Doctor doctor);

    Doctor selectByUserId(@Param("userId") Integer userId);
}