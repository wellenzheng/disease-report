package com.example.diseasereport.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengweijun
 * Created on 2020-07-01
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(Include.ALWAYS)
public class CommonResponse<T> {

    private Integer code;
    private String message;
    private T result;

    public static <R> CommonResponse<R> success(String message, R response) {
        return CommonResponse.of(200, message, response);
    }

    public static <R> CommonResponse<R> error(String message, R response) {
        return CommonResponse.of(500, message, response);
    }
}
