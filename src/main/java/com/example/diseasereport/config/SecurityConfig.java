package com.example.diseasereport.config;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.diseasereport.common.CommonResponse;
import com.example.diseasereport.model.User;
import com.example.diseasereport.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhengweijun
 * Created on 2020-07-01
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    User user = (User) authentication.getPrincipal();
                    String response = new ObjectMapper().writeValueAsString(CommonResponse
                            .success("登陆成功", user));
                    writer.write(response);
                    writer.flush();
                    writer.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, exception) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    CommonResponse commonResponse = CommonResponse.error("登陆失败", null);
                    if (exception instanceof LockedException) {
                        commonResponse.setMessage("账户被锁定");
                    } else if (exception instanceof CredentialsExpiredException) {
                        commonResponse.setMessage("密码已过期");
                    } else if (exception instanceof AccountExpiredException) {
                        commonResponse.setMessage("账户已过期");
                    } else if (exception instanceof DisabledException) {
                        commonResponse.setMessage("账户被禁用");
                    } else if (exception instanceof BadCredentialsException) {
                        commonResponse.setMessage("用户名或密码错误");
                    }
                    writer.write(new ObjectMapper().writeValueAsString(commonResponse));
                    writer.flush();
                    writer.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    if (authentication != null) {
                        User user = (User) authentication.getPrincipal();
                        writer.write(new ObjectMapper().writeValueAsString(CommonResponse
                                .success("注销成功", user)));
                    } else {
                        writer.write(new ObjectMapper().writeValueAsString(CommonResponse
                                .error("尚未登陆", null)));
                    }
                })
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, exception) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    CommonResponse response = CommonResponse.error("访问失败", null);
                    if (exception instanceof InsufficientAuthenticationException) {
                        response.setMessage("请求失败");
                    }
                    writer.write(new ObjectMapper().writeValueAsString(response));
                });
    }
}
