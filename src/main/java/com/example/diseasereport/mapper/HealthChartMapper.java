package com.example.diseasereport.mapper;

import com.example.diseasereport.model.HealthChart;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HealthChartMapper {
    int deleteById(Integer id);

    int insert(HealthChart healthChart);

    HealthChart selectById(Integer id);

    List<HealthChart> selectAll();

    int updateById(HealthChart healthChart);
}