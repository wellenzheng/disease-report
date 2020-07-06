package com.example.diseasereport.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengweijun
 * Created on 2020-07-06
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBySeverity {
    private String severity;
    private Integer count;
}
