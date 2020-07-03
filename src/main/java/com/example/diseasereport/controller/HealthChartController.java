package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonIdResponse;
import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.HealthChart;
import com.example.diseasereport.service.HealthChartService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@RestController
@RequestMapping("/healthChart")
public class HealthChartController {

    @Autowired
    private HealthChartService healthChartService;

    @PostMapping("/upload")
    @ApiOperation(value = "uploadHealthChart", tags = "上报健康信息表")
    public CommonResponse<CommonIdResponse> uploadHealthChart(
            @ApiParam(name = "healthChart", value = "健康信息表") @RequestBody HealthChart healthChart
    ) {
        return CommonResponse.success("上报成功",
                CommonIdResponse.builder().healthChartId(healthChartService.insert(healthChart)).build());
    }
}
