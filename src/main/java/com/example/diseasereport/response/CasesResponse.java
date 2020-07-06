package com.example.diseasereport.response;

import io.swagger.annotations.ApiModelProperty;
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
@AllArgsConstructor
@NoArgsConstructor
public class CasesResponse {
    @ApiModelProperty("病例id")
    private Integer casesId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("医生id")
    private Integer doctorId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户学工号")
    private String schoolId;

    @ApiModelProperty("用户性别")
    private String gender;

    @ApiModelProperty("用户年龄")
    private Integer age;

    @ApiModelProperty("所在学院")
    private String department;

    @ApiModelProperty("医生姓名")
    private String doctorName;

    @ApiModelProperty("医生电话")
    private String doctorPhone;

    @ApiModelProperty("病人状态")
    private String status;

    @ApiModelProperty("危重程度")
    private String severity;

    @ApiModelProperty("更新时间")
    private String updateTime;
}
