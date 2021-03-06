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
        if (redisUtils.hasKey(prefix + cases.getUserId())) {
            return null;
        }
        int i = casesMapper.insert(cases);
        if (i != 0) {
            CasesResponse casesResponse = casesMapper.selectByUserId(cases.getUserId());
            redisUtils.set(prefix + cases.getUserId(), casesResponse);
            redisUtils.lRightPush(prefix + "all", casesResponse);
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

    public CasesResponse getByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            return null;
        }
        CasesResponse casesResponse = (CasesResponse) redisUtils.get(prefix + userId);
        if (casesResponse != null) {
            return casesResponse;
        }
        CasesResponse response = casesMapper.selectByUserId(userId);
        if (response != null) {
            redisUtils.set(prefix + userId, response);
        }
        return response;
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
            redisUtils.lRightPushAll(prefix + "all", casesResponseList.toArray());
        }
        return casesResponseList == null || casesResponseList.size() == 0 ? null : casesResponseList;
    }

    public Integer editByUserId(Cases cases) {
        if (cases == null || cases.getUserId() == null) {
            return null;
        }
        CasesResponse c = casesMapper.selectByUserId(cases.getUserId());
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
        int i = casesMapper.updateByUserId(cases);
        CasesResponse casesResponse = casesMapper.selectByUserId(cases.getUserId());
        if (i != 0) {
            redisUtils.set(prefix + cases.getUserId(), casesResponse);
            if (redisUtils.hasKey(prefix + "all")) {
                redisUtils.lUpdateIndex(prefix + "all", casesResponse.getCasesId() - 1, casesResponse);
            }
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
