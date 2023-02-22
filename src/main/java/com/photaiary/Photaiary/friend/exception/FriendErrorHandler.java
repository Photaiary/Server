package com.photaiary.Photaiary.friend.exception;


import com.photaiary.Photaiary.friend.exception.custom.AlreadyInitializedExceptionFriend;
import com.photaiary.Photaiary.friend.exception.custom.ToUserNotFoundExceptionFriend;
import com.photaiary.Photaiary.global.exception.ExceptionResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@NoArgsConstructor
public class FriendErrorHandler {//👨‍💻

    private Exception exception;
    private WebRequest request;

    public FriendErrorHandler(Exception e, WebRequest webRequest) {
        this.exception = e;
        this.request = webRequest;
    }

    public ExceptionResponse handleError(){
        if(exception instanceof ToUserNotFoundExceptionFriend){
            return handleUserNotFound();
        } else if (exception instanceof AlreadyInitializedExceptionFriend) {
            return handleAlreadyInitializedException();
        } else {
            return null;
        }
    }

    public ExceptionResponse handleUserNotFound(){
        return new ExceptionResponse(false,new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getDescription(false));
    }

    public ExceptionResponse handleAlreadyInitializedException(){
        return new ExceptionResponse(false,new Date(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getDescription(false));
    }
}
