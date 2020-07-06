package com.example.diseasereport.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.diseasereport.model.Cases;
import com.example.diseasereport.response.CasesResponse;
import com.example.diseasereport.response.GroupByAge;
import com.example.diseasereport.response.GroupByGender;
import com.example.diseasereport.response.GroupBySeverity;

@Repository
@Mapper
public interface CasesMapper {
    int deleteById(Integer id);

    int insert(Cases record);

    Cases selectById(Integer id);

    List<CasesResponse> selectAll();

    int updateById(Cases record);

    CasesResponse selectByUserId(@Param("userId") Integer userId);

    int updateByUserId(Integer userId);

    long countAll();

    List<GroupByAge> groupByAge();

    List<GroupByGender> groupByGender();

    List<GroupBySeverity> groupBySeverity();
}