package com.photaiary.Photaiary.user.exception;

// HTTP Status code
// 2XX -> OK
// 4XX -> Client Error
// 5XX -> Server

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  // 404 Not Found
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
