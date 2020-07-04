package com.example.diseasereport.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengweijun
 * Created on 2020-07-04
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoAndHealth {
    private Integer userId;

    private String schoolId;

    private String name;

    private String department;

    private String phone;

    private String urgentPhone;

    private String isHKMT;

    private String isInternational;

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
