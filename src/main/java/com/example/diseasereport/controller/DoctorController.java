package com.example.diseasereport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.Doctor;
import com.example.diseasereport.response.InfoAndHealth;
import com.example.diseasereport.service.DoctorService;
import com.example.diseasereport.service.SynchronizeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-04
 */

@RestController
@RequestMapping("/doctor")
@Api(value = "医生信息控制器", tags = "DoctorController")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SynchronizeService synchronizeService;

    @GetMapping("/get")
    @ApiOperation(value = "getDoctorInfo")
    public CommonResponse<Doctor> getDoctorInfo(
            @ApiParam(name = "userId", value = "用户id") @RequestParam Integer userId
    ) {
        return CommonResponse.success("获取医生信息", doctorService.getByUserId(userId));
    }

    @GetMapping("/getAllInfoAndHealth")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @ApiOperation(value = "getAllInfoAndHealth")
    public CommonResponse<List<InfoAndHealth>> getAllInfoAndHealth() {
        return CommonResponse.success("获取所有用户信息和健康表", doctorService.getAllInfoAndHealth());
    }

    @GetMapping("/synchronize")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @ApiOperation(value = "synchronize")
    public CommonResponse<String> synchronize() {
        synchronizeService.synchronize();
        return CommonResponse.success("同步统计数据", "同步成功");
    }
}
