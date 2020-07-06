package com.example.diseasereport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonIdResponse;
import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.Cases;
import com.example.diseasereport.response.CasesResponse;
import com.example.diseasereport.response.GenderAgeSeverity;
import com.example.diseasereport.service.CasesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-05
 */

@RestController
@RequestMapping("/cases")
@Api(value = "病例控制器", tags = "CasesController")
public class CasesController {

    @Autowired
    private CasesService casesService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @ApiOperation(value = "addCases")
    public CommonResponse<CommonIdResponse> addCases(
            @ApiParam(name = "cases", value = "病例") @RequestBody Cases cases
    ) {
        return CommonResponse.success("添加病例", CommonIdResponse.builder().casesId(casesService.addCases(cases)).build());
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @ApiOperation(value = "getAllCases")
    public CommonResponse<List<CasesResponse>> getAllCases() {
        return CommonResponse.success("获取所有病例", casesService.getAll());
    }

    @GetMapping("/get")
    @ApiOperation(value = "getByUserId")
    public CommonResponse<Cases> getByUserId(Integer userId) {
        return CommonResponse.success("获取个人病例", casesService.getByUserId(userId));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @ApiOperation(value = "editCases")
    public CommonResponse<CommonIdResponse> editCases(
            @ApiParam(name = "cases", value = "病例") @RequestBody Cases cases
    ) {
        return CommonResponse
                .success("编辑病例", CommonIdResponse.builder().casesId(casesService.editByUserId(cases)).build());
    }

    @GetMapping("/getGenAgeSev")
    @ApiOperation(value = "getGenAgeSev")
    public CommonResponse<GenderAgeSeverity> getGenAgeSev() {
        return CommonResponse.success("根据性别年龄病危程度统计", casesService.getGenAgeSev());
    }
}
