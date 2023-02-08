package com.photaiary.Photaiary.post.photo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PhotoErrorHandler extends RuntimeException{
    public PhotoErrorHandler() {
        super();
    }

    public PhotoErrorHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoErrorHandler(String message) {
        super(message);
    }

    public PhotoErrorHandler(Throwable cause) {
        super(cause);
    }
}
