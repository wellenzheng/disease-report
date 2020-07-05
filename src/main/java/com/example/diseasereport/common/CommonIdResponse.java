package com.example.diseasereport.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengweijun
 * Created on 2020-07-03
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(Include.ALWAYS)
public class CommonIdResponse {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("医生id")
    private Integer doctorId;

    @ApiModelProperty("用户基础信息id")
    private Integer userInfoId;

    @ApiModelProperty("健康表id")
    private Integer healthChartId;

    @ApiModelProperty("病例id")
    private Integer casesId;

    @ApiModelProperty("统计信息id")
    private Integer statisticId;
}
