package com.example.diseasereport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cases {
    private Integer id;

    private Integer userId;

    private Integer doctorId;

    private String status;

    private String severity;

    private String updateTime;
}