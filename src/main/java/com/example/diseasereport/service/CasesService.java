package com.example.diseasereport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.CasesMapper;
import com.example.diseasereport.model.Cases;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-05
 */

@Slf4j
@Service
public class CasesService {

    private final String prefix = "cases:";

    @Autowired
    private CasesMapper casesMapper;

    @Autowired
    private RedisUtils redisUtils;

    public Integer addCases(Cases cases) {
        int i = casesMapper.insert(cases);
        if (i != 0) {
            redisUtils.set(prefix + cases.getUserId(), cases);
            redisUtils.lSet(prefix + "all", cases);
        }
        return i;
    }

    public Cases getByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            return null;
        }
        Cases cases = (Cases) redisUtils.get(prefix + userId);
        if (cases != null) {
            return cases;
        }
        Cases c = casesMapper.selectByUserId(userId);
        if (c != null) {
            redisUtils.set(prefix + c.getUserId(), c);
        }
        return c;
    }

    public List<Cases> getAll() {
        List<Object> objectList = redisUtils.lGet(prefix + "all", 0, -1);
        if (objectList != null && objectList.size() != 0) {
            return RedisUtils.castList(objectList, Cases.class);
        }
        List<Cases> casesList = casesMapper.selectAll();
        if (casesList != null && casesList.size() != 0) {
            redisUtils.delete(prefix + "all");
            redisUtils.lSetAll(prefix + "all", casesList.toArray());
        }
        return casesList;
    }

    public Integer editByUserId(Cases cases) {
        if (cases == null || cases.getUserId() == null) {
            return null;
        }
        int i = casesMapper.updateByUserId(cases.getUserId());
        if (i != 0) {
            redisUtils.set(prefix + cases.getUserId(), cases);
        }
        return i;
    }
}
