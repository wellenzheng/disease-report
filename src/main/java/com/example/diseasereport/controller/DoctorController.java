package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.Doctor;
import com.example.diseasereport.service.DoctorService;

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

    @GetMapping("/get")
    @ApiOperation(value = "getDoctorInfo")
    public CommonResponse<Doctor> getDoctorInfo(
            @ApiParam(name = "userId", value = "用户id") @RequestParam Integer userId
    ) {
        return CommonResponse.success("获取医生信息", doctorService.getByUserId(userId));
    }
}
