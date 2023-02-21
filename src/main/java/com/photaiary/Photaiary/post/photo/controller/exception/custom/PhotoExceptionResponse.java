package com.photaiary.Photaiary.post.photo.controller.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoExceptionResponse {
    private Integer statusCode;
    private Long errorCode;
    private String message;
    private Boolean isSuccess;
}
