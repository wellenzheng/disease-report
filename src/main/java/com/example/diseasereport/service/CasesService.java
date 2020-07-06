package com.example.diseasereport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.CasesMapper;
import com.example.diseasereport.model.Cases;
import com.example.diseasereport.response.CasesResponse;
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
                severityJudge(cases.getSeverity());
            } else if (cases.getStatus().equals("确诊")) {
                redisUtils.incr("totalDiagnose", 1);
                redisUtils.incr("currDiagnose", 1);
                redisUtils.incr("newDiagnose", 1);
                severityJudge(cases.getSeverity());
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

    public List<CasesResponse> getAll() {
        Long size = redisUtils.lGetListSize(prefix + "all");
        Long countAll = casesMapper.countAll();
        if (size.equals(countAll)) {
            List<Object> objectList = redisUtils.lGet(prefix + "all", 0, -1);
            if (objectList != null && objectList.size() != 0) {
                return RedisUtils.castList(objectList, CasesResponse.class);
            }
        }
        List<CasesResponse> casesResponseList = casesMapper.selectAll();
        if (casesResponseList != null && casesResponseList.size() != 0) {
            redisUtils.delete(prefix + "all");
            redisUtils.lSetAll(prefix + "all", casesResponseList.toArray());
        }
        return casesResponseList;
    }

    public Integer editByUserId(Cases cases) {
        if (cases == null || cases.getUserId() == null) {
            return null;
        }
        Cases c = casesMapper.selectByUserId(cases.getUserId());
        if (c == null) {
            return null;
        }
        if (c.getStatus().equals("疑似") && cases.getStatus().equals("确诊")) {
            redisUtils.incr("totalDiagnose", 1);
            redisUtils.incr("currDiagnose", 1);
            redisUtils.incr("newDiagnose", 1);
            redisUtils.decr("currSuspect", 1);
            severityChange(c.getSeverity(), cases.getSeverity());
        } else if (c.getStatus().equals("确诊") && cases.getStatus().equals("治愈")) {
            redisUtils.incr("totalCure", 1);
            redisUtils.incr("newCure", 1);
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

    private void severityJudge(String sev) {
        if (sev == null) {
            return;
        }
        if (sev.equals("无症状")) {
            redisUtils.incr("currAsy", 1);
        } else if (sev.equals("重症")) {
            redisUtils.incr("currSevere", 1);
        }
    }

    private void severityChange(String oldSev, String newSev) {
        if (oldSev == null || newSev == null || oldSev.equals(newSev)) {
            return;
        }
        if (newSev.equals("无症状")) {
            redisUtils.incr("currAsy", 1);
            if (oldSev.equals("重症")) {
                redisUtils.decr("currSevere", 1);
            }
        } else if (oldSev.equals("无症状")) {
            redisUtils.decr("currAsy", 1);
            if (newSev.equals("重症")) {
                redisUtils.incr("currSevere", 1);
            }
        }
        if (newSev.equals("重症")) {
            redisUtils.incr("currSevere", 1);
            if (oldSev.equals("无症状")) {
                redisUtils.decr("currAsy", 1);
            }
        } else if (oldSev.equals("重症")) {
            redisUtils.decr("currSevere", 1);
            if (newSev.equals("无症状")) {
                redisUtils.incr("currAsy", 1);
            }
        }
    }
}
