package com.example.diseasereport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diseasereport.mapper.StatisticMapper;
import com.example.diseasereport.model.Statistic;
import com.example.diseasereport.utils.RedisUtils;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-04
 */

@Slf4j
@Service
public class StatisticService {

    private final String prefix = "statistic:";

    private final String[] areaList = {"北京", "香港", "上海", "四川", "河北", "甘肃", "陕西", "辽宁", "广东", "台湾",
            "福建", "重庆", "浙江", "江苏", "天津", "云南", "澳门", "湖北", "河南", "湖南", "安徽", "黑龙江", "江西",
            "山东", "广西", "内蒙古", "山西", "海南", "吉林", "贵州", "新疆", "宁夏", "青海", "西藏"};

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private RedisUtils redisUtils;

    public List<Statistic> getAll() {
        List<Object> allStatistics = redisUtils.lGet(prefix + "all", 0, -1);
        if (allStatistics != null && allStatistics.size() != 0) {
            return RedisUtils.castList(allStatistics, Statistic.class);
        }
        List<Statistic> statisticList = statisticMapper.selectAll();
        if (statisticList != null && statisticList.size() != 0) {
            redisUtils.lSetAll("allStatistics", statisticList.toArray());
        }
        return statisticList;
    }

    public List<Statistic> getAllGroupByDate() {
        List<Object> statistics = redisUtils.lGet(prefix + "groupByDate", 0, -1);
        if (statistics != null && statistics.size() != 0) {
            return RedisUtils.castList(statistics, Statistic.class);
        }
        List<Statistic> statisticList = statisticMapper.selectGroupByDate();
        if (statisticList != null && statisticList.size() != 0) {
            redisUtils.lSetAll(prefix + "groupByDate", statisticList.toArray());
        }
        return statisticList;
    }

//    public Map<String, List<Statistic>> getAllGroupByArea() {
//        Map<String, List<Statistic>> map = new HashMap<>();
//        for (String area : areaList) {
//            List<Object> objectList = redisUtils.lGet(area, 0, -1);
//            map.put(area, RedisUtils.castList(objectList, Statistic.class));
//        }
//        List<Statistic> statisticList = statisticMapper.selectAll();
//        for (Statistic statistic : statisticList) {
//            if (!map.containsKey(statistic.getArea())) {
//                map.put(statistic.getArea(), Lists.newArrayList(statistic));
//            } else {
//                List<Statistic> statistics = map.get(statistic.getArea());
//                statistics.add(statistic);
//                map.replace(statistic.getArea(), statistics);
//            }
//        }
//        map.forEach(((area, statistics) -> {
//            redisUtils.delete(area);
//            redisUtils.lSet(area, statistics);
//        }));
//        return map;
//    }
}
