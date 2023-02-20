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

    public FriendErrorHandler(Exception e, WebRequest webRequest) {
        this.exception = e;
        this.request = webRequest;
    }

    public ExceptionResponse handleError(){
        if(exception instanceof UserNotFoundException){
            return handleUserNotFound();
        }else {
            return null;
        }
    }

    public ExceptionResponse handleUserNotFound(){
        System.out.println(1);
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getDescription(false));
        return exceptionResponse;
    }
}
