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

    private Boolean isConCaseIn14;

    private Boolean isPassRiskAreaIn14;

    private Boolean isAbroadIn14;

    private Boolean isConRiskAbrIn14;

    private String details;

    private String reportDate;
}