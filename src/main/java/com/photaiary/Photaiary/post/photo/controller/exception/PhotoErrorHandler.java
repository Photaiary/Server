package com.photaiary.Photaiary.post.photo.controller.exception;

import com.photaiary.Photaiary.global.exception.ExceptionResponse;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.*;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@NoArgsConstructor
public class PhotoErrorHandler {
    private Exception exception;
    private WebRequest webRequest;

    public PhotoErrorHandler(Exception e, WebRequest webRequest) {
        this.exception = e;
        this.webRequest = webRequest;
    }

    public PhotoExceptionResponse handleError() {
        if (exception instanceof NoUserException) {
            return handleNoUserError();
        } else if (exception instanceof VoException) {
            return handleVoError();
        } else if (exception instanceof NoLocationException) {
            return handleNoLocationError();
        } else if (exception instanceof FileConvertException) {
            return handleFileConvertError();
        }
        return null;
    }

    private PhotoExceptionResponse handleNoUserError() {
        System.out.println(1);
        return new PhotoExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),1L, exception.getMessage(), false);
    }

    private PhotoExceptionResponse handleVoError() {
        System.out.println(2);
        return new PhotoExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),2L, exception.getMessage(), false);
    }

    private PhotoExceptionResponse handleNoLocationError() {
        System.out.println(3);
        return new PhotoExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),3L, exception.getMessage(), false);
    }

    private PhotoExceptionResponse handleFileConvertError() {
        System.out.println(4);
        return new PhotoExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),4L, exception.getMessage(), false);
    }
}
