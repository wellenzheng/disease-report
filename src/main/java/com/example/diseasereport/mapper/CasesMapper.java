package com.example.diseasereport.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.Cases;

@Repository
@Mapper
public interface CasesMapper {
    int deleteById(Integer id);

    int insert(Cases record);

    Cases selectById(Integer id);

    List<Cases> selectAll();

    int updateById(Cases record);

    Cases selectByUserId(@Param("userId") Integer userId);

    int updateByUserId(Integer userId);

    long countAll();

    @MapKey("age")
    Map<Integer, Integer> groupByAge();

    @MapKey("gender")
    Map<String, Integer> groupByGender();

    @MapKey("severity")
    Map<String, Integer> groupBySeverity();
}