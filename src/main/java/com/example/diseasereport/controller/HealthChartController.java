package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonIdResponse;
import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.HealthChart;
import com.example.diseasereport.service.HealthChartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@RestController
@RequestMapping("/healthChart")
@Api(value = "健康表控制器", tags = "HealthChartController")
public class HealthChartController {

    @Autowired
    private HealthChartService healthChartService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('STUDENT','TEACHER')")
    @ApiOperation(value = "uploadHealthChart")
    public CommonResponse<CommonIdResponse> uploadHealthChart(
            @ApiParam(name = "healthChart", value = "健康信息表") @RequestBody HealthChart healthChart
    ) {
        return CommonResponse.success("上报成功",
                CommonIdResponse.builder().healthChartId(healthChartService.addHealthChart(healthChart)).build());
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('STUDENT','TEACHER')")
    @ApiOperation(value = "getHealthChart")
    public CommonResponse<HealthChart> getHealthChart(
            @ApiParam(name = "userId", value = "用户id") @RequestParam Integer userId
    ) {
        return CommonResponse.success("个人健康表", healthChartService.getByUserId(userId));
    }
}
