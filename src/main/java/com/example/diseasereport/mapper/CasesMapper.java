package com.example.diseasereport.mapper;

import com.example.diseasereport.model.Cases;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CasesMapper {
    int deleteById(Integer id);

    int insert(Cases record);

    Cases selectById(Integer id);

    List<Cases> selectAll();

    int updateById(Cases record);

    Cases selectByUserId(@Param("userId") Integer userId);
}