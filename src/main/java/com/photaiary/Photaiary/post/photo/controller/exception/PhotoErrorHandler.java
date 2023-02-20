package com.photaiary.Photaiary.post.photo.controller.exception;

import com.photaiary.Photaiary.global.exception.ExceptionResponse;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.FileConvertException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoLocationException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    public ExceptionResponse handleError() {
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

    private ExceptionResponse handleNoUserError() {
        System.out.println(1);
        return new ExceptionResponse(false, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), webRequest.getDescription(false));
    }

    private ExceptionResponse handleVoError() {
        System.out.println(2);
        return new ExceptionResponse(false, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), webRequest.getDescription(false));
    }

    private ExceptionResponse handleNoLocationError() {
        System.out.println(3);
        return new ExceptionResponse(false, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), webRequest.getDescription(false));
    }

    private ExceptionResponse handleFileConvertError() {
        System.out.println(4);
        return new ExceptionResponse(false, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), webRequest.getDescription(false));
    }
}
