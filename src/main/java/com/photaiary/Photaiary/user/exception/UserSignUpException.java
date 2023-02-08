package com.photaiary.Photaiary.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserSignUpException extends RuntimeException {
    public UserSignUpException(String message) {
        super(message);
    }
}