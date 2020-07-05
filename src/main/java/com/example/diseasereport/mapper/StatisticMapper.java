package com.example.diseasereport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.Statistic;

@Repository
@Mapper
public interface StatisticMapper {
    int deleteById(Integer id);

    int insert(Statistic record);

    Statistic selectById(Integer id);

    List<Statistic> selectAll();

    int updateById(Statistic record);

    List<Statistic> selectGroupByDate();
}