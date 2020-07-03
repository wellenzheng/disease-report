package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.service.UserInfoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/edit")
    @ApiOperation(value = "updateByUserId", tags = "根据用户id更新用户基础信息")
    public CommonResponse<Integer> updateByUserId(
            @ApiParam(name = "userInfo", value = "用户基础信息") @RequestBody UserInfo userInfo
    ) {
        return CommonResponse.success("200", userInfoService.updateByUserId(userInfo));
    }
}
