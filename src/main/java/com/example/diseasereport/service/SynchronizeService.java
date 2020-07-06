package com.example.diseasereport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.StatisticMapper;
import com.example.diseasereport.model.Statistic;
import com.example.diseasereport.response.GroupBySeverity;
import com.example.diseasereport.utils.DateFormatUtils;
import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-06
 */

@Slf4j
@Service
@EnableScheduling
public class SynchronizeService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CasesService casesService;

    @Autowired
    private StatisticMapper statisticMapper;

    @Scheduled(cron = "0 59 23 * * ?")
    public void synchronize() {
        Statistic currStatistic = getCurrStatistic();
        statisticMapper.insertOrUpdate(currStatistic);
        Statistic statistic = (Statistic) redisUtils.lRightPop("statistic:groupByDate");
        if (!statistic.getUpdateDate().equals(currStatistic.getUpdateDate())) {
            redisUtils.lRightPush("statistic:groupByDate", statistic);
        }
        redisUtils.lRightPush("statistic:groupByDate", currStatistic);
        redisUtils.delete("newSuspect", "newDiagnose", "newDeath", "newCure");
    }

    public Statistic getCurrStatistic() {
        List<GroupBySeverity> groupBySeverityList = casesService.getGenAgeSev().getGroupBySeverity();
        Integer asy = null;
        Integer sev = null;
        for (GroupBySeverity groupBySeverity : groupBySeverityList) {
            if (groupBySeverity.getSeverity().equals("无症状")) {
                asy = groupBySeverity.getCount();
            }
            if (groupBySeverity.getSeverity().equals("重症")) {
                sev = groupBySeverity.getCount();
            }
        }
        Integer totalDiagnose = (Integer) redisUtils.get("totalDiagnose");
        Integer totalDeath = (Integer) redisUtils.get("totalDeath");
        Integer totalCure = (Integer) redisUtils.get("totalCure");
        Integer currDiagnose = (Integer) redisUtils.get("currDiagnose");
        Integer currSuspect = (Integer) redisUtils.get("currSuspect");
        Integer currAsy = (Integer) redisUtils.get("currAsy");
        Integer currSevere = (Integer) redisUtils.get("currSevere");
        Integer newSuspect = (Integer) redisUtils.get("newSuspect");
        Integer newDiagnose = (Integer) redisUtils.get("newDiagnose");
        Integer newDeath = (Integer) redisUtils.get("newDeath");
        Integer newCure = (Integer) redisUtils.get("newCure");
        if (asy != null && !asy.equals(currAsy)) {
            currAsy = asy;
            redisUtils.set("currAsy", currAsy);
        }
        if (sev != null && !sev.equals(currSevere)) {
            currSevere = sev;
            redisUtils.set("currSevere", currSevere);
        }
        return Statistic.builder()
                .totalDiagnose(totalDiagnose == null ? 0 : totalDiagnose)
                .totalDeath(totalDeath == null ? 0 : totalDeath)
                .totalCure(totalCure == null ? 0 : totalCure)
                .currDiagnose(currDiagnose == null ? 0 : currDiagnose)
                .currSuspect(currSuspect == null ? 0 : currSuspect)
                .currSevere(currSevere == null ? 0 : currSevere)
                .currAsy(currAsy == null ? 0 : currAsy)
                .newSuspect(newSuspect == null ? 0 : newSuspect)
                .newDiagnose(newDiagnose == null ? 0 : newDiagnose)
                .newDeath(newDeath == null ? 0 : newDeath)
                .newCure(newCure == null ? 0 : newCure)
                .updateDate(DateFormatUtils.getDate(new Date()))
                .build();
    }
}
