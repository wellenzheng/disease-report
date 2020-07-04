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
public class HealthChart {
    @Id
    private Integer id;

    private Integer userId;

    private String selfStatus;

    private String familyStatus;

    private String location;

    private String isConCaseIn14;

    private String isPassRiskAreaIn14;

    private String isAbroadIn14;

    private String isConRiskAbrIn14;

    private String details;

    private String reportDate;
}