package com.example.diseasereport.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    private Integer id;

    private Integer userId;

    private String schoolId;

    private String name;

    private String department;

    private String phone;

    private String urgentPhone;

    private String isHKMT;

    private String isInternational;
}