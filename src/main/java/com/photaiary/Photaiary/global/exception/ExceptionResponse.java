package com.photaiary.Photaiary.global.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {  // 일반화 된 예외 클래스
    @ApiModelProperty(value = "응답 성공 여부")
    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    private Date timestamp;
    private int statusCode;
    private String message;
    private String details;  // 어디서 예외가 발생했는지
}