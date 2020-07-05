package com.example.diseasereport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.CasesMapper;
import com.example.diseasereport.model.Cases;
import com.example.diseasereport.response.GenderAgeSeverity;
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
            if (cases.getStatus().equals("疑似")) {
                redisUtils.incr("currSuspect", 1);
                redisUtils.incr("newSuspect", 1);
                if (cases.getSeverity().equals("无症状")) {
                    redisUtils.incr("currAsy", 1);
                }
            } else if (cases.getStatus().equals("确诊")) {
                redisUtils.incr("totalDiagnose", 1);
                redisUtils.incr("currDiagnose", 1);
                redisUtils.incr("newDiagnose", 1);
                if (cases.getSeverity().equals("无症状")) {
                    redisUtils.incr("currAsy", 1);
                }
            }
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
        Long size = redisUtils.lGetListSize(prefix + "all");
        Long countAll = casesMapper.countAll();
        if (size.equals(countAll)) {
            List<Object> objectList = redisUtils.lGet(prefix + "all", 0, -1);
            if (objectList != null && objectList.size() != 0) {
                return RedisUtils.castList(objectList, Cases.class);
            }
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
        Cases c = casesMapper.selectByUserId(cases.getUserId());
        if (c.getStatus().equals("疑似") && cases.getStatus().equals("确诊")) {
            redisUtils.incr("totalDiagnose", 1);
            redisUtils.incr("currDiagnose", 1);
            redisUtils.incr("newDiagnose", 1);
            redisUtils.decr("currSuspect", 1);
            if (!c.getSeverity().equals("无症状") && cases.getSeverity().equals("无症状")) {
                redisUtils.incr("currAsy", 1);
            } else if (c.getSeverity().equals("无症状") && !cases.getSeverity().equals("无症状")) {
                redisUtils.decr("currAsy", 1);
            }
        } else if (c.getStatus().equals("确诊") && cases.getStatus().equals("治愈")) {
            redisUtils.decr("currDiagnose", 1);
        } else if (c.getStatus().equals("确诊") && cases.getStatus().equals("死亡")) {
            redisUtils.decr("currDiagnose", 1);
            redisUtils.incr("totalDeath", 1);
            redisUtils.incr("newDeath", 1);
        }
        int i = casesMapper.updateByUserId(cases.getUserId());
        if (i != 0) {
            redisUtils.set(prefix + cases.getUserId(), cases);
            redisUtils.lUpdateIndex(prefix + "all", c.getId(), cases);
        }
        return i;
    }

    public GenderAgeSeverity getGenAgeSev() {
        return GenderAgeSeverity.builder()
                .groupByAge(casesMapper.groupByAge())
                .groupByGender(casesMapper.groupByGender())
                .groupBySeverity(casesMapper.groupBySeverity())
                .build();
    }
}
