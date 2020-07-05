package com.example.diseasereport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.HealthChart;

@Repository
@Mapper
public interface HealthChartMapper {
    int deleteById(Integer id);

    int insert(HealthChart healthChart);

    HealthChart selectById(Integer id);

    List<HealthChart> selectAll();

    int updateById(HealthChart healthChart);

    HealthChart selectByUserId(@Param("userId") Integer userId);

    void updateAutoIncr();
}