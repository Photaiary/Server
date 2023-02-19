package com.photaiary.Photaiary.friend.exception;


import com.photaiary.Photaiary.friend.exception.custom.UserNotFoundException;
import com.photaiary.Photaiary.global.exception.ExceptionResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@NoArgsConstructor
public class FriendErrorHandler {

    private Exception exception;
    private WebRequest request;

    public FriendErrorHandler(Exception exception, WebRequest request) {
        this.exception = exception;
        this.request = request;
    }

    public ExceptionResponse handleError(){
        if(exception instanceof UserNotFoundException){
            return handleUserNotFound();
        }else {
            return null;
        }
    }


    public ExceptionResponse handleUserNotFound(){
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getDescription(false));
        return exceptionResponse;
    }
}
