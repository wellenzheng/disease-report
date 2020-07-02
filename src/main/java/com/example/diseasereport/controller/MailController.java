package com.example.diseasereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.service.MailService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhengweijun
 * Created on 2020-07-02
 */

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/getCode")
    @ApiOperation(value = "getCode", tags = "获取验证码")
    public void getCode(
            @ApiParam(name = "mail", value = "邮箱") @RequestParam String mail
    ) {
        mailService.sendEmail(mail);
    }
}
