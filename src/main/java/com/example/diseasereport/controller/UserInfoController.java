package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.UserInfo;
import com.example.diseasereport.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@RestController
@RequestMapping("/userInfo")
@Api(value = "用户基础信息控制器", tags = "UserInfoController")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('STUDENT','TEACHER')")
    @ApiOperation(value = "editUserInfo")
    public CommonResponse<Integer> editUserInfo(
            @ApiParam(name = "userInfo", value = "用户基础信息") @RequestBody UserInfo userInfo
    ) {
        return CommonResponse.success("编辑用户基础信息", userInfoService.insertOrUpdate(userInfo));
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('STUDENT','TEACHER')")
    @ApiOperation(value = "getUserInfo")
    public CommonResponse<UserInfo> getUserInfo(
            @ApiParam(name = "userId", value = "用户id") @RequestParam Integer userId
    ) {
        return CommonResponse.success("获取用户基础信息", userInfoService.getByUserId(userId));
    }
}
