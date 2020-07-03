package com.example.diseasereport.mapper;

import com.example.diseasereport.model.Doctor;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DoctorMapper {
    int deleteById(Integer id);

    int insert(Doctor doctor);

    Doctor selectById(Integer id);

    List<Doctor> selectAll();

    int updateById(Doctor doctor);
}