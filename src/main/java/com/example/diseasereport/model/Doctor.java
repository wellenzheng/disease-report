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
public class Doctor {
    @Id
    private Integer id;

    private Integer userId;

    private String name;

    private String department;

    private String occupation;

    private String phone;
}