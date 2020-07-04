package com.example.diseasereport.mapper;

import com.example.diseasereport.model.Statistic;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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