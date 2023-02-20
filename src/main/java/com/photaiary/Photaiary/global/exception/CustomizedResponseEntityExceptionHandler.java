package com.photaiary.Photaiary.global.exception;

import com.photaiary.Photaiary.post.photo.controller.exception.PhotoErrorHandler;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 Controller 가 실행될 때 반드시 실행됨
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
//        ExceptionResponse exceptionResponse =
//                new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 error
//    }
    @ExceptionHandler(VoException.class)
    public final ResponseEntity<Object> handlePhotoExceptions(Exception ex, WebRequest request) {
        PhotoErrorHandler photoErrorHandler = new PhotoErrorHandler(ex, request);
        ExceptionResponse exceptionResponse = photoErrorHandler.handleError();
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 error
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(false, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 error
    }
    @Override  // 부모가 가진 method를 재정의
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(false,new Date(), HttpStatus.BAD_REQUEST.value(),
                        "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}