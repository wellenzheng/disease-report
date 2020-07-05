package com.example.diseasereport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.Statistic;
import com.example.diseasereport.service.StatisticService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhengweijun
 * Created on 2020-07-04
 */

@RestController
@RequestMapping("/statistic")
@Api(value = "统计信息控制器", tags = "StatisticController")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/getGroupByDate")
    @ApiOperation(value = "getGroupByDate")
    public CommonResponse<List<Statistic>> getGroupByDate() {
        return CommonResponse.success("获取每一天疫情汇总", statisticService.getAllGroupByDate());
    }
}
