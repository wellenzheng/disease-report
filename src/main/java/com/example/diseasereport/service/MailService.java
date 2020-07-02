package com.example.diseasereport.service;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.example.diseasereport.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengweijun
 * Created on 2020-07-02
 */

@EnableAsync
@Slf4j
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendEmail(String mail) {
        if (!mail.endsWith("scut.edu.cn") && !mail.endsWith("mail.scut.edu.cn")) {
            return;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String code = generateCode();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail);
            helper.setSubject("邮箱验证码");
            helper.setText("您的邮箱验证码为：" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (redisUtils.set(mail, code, 60 * 5)) {
            mailSender.send(mimeMessage);
        }
    }

    private String generateCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }
}
