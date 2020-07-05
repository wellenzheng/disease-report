package com.example.diseasereport.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengweijun
 * Created on 2020-07-05
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenderAgeSeverity {
    private Map<Integer, Integer> groupByAge;

    private Map<String, Integer> groupByGender;

    private Map<String, Integer> groupBySeverity;
}
