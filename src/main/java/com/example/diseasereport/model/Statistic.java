package com.example.diseasereport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private Integer id;

    private Integer totalDiagnose;

    private Integer totalDeath;

    private Integer totalCure;

    private Integer currDiagnose;

    private Integer currSuspect;

    private Integer currAsy;

    private Integer newDiagnose;

    private String area;

    private String updateDate;
}