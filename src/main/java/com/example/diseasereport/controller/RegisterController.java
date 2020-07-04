package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.request.UserRequest;
import com.example.diseasereport.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-02
 */

@RestController
@Api(value = "注册控制器", tags = "RegisterController")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "register")
    public CommonResponse<Integer> register(
            @ApiParam(name = "userRequest", value = "用户注册信息") @RequestBody UserRequest userRequest
    ) {
        Integer integer = userService.register(userRequest);
        if (integer == -1) {
            return CommonResponse.error("用户已存在", integer);
        } else if (integer == 0) {
            return CommonResponse.error("验证码错误", integer);
        } else {
            return CommonResponse.success("注册成功", integer);
        }
    }
}
