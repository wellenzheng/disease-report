package com.example.diseasereport.response;

import java.util.List;

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
    private List<GroupByAge> groupByAge;

    private List<GroupByGender> groupByGender;

    private List<GroupBySeverity> groupBySeverity;
}
