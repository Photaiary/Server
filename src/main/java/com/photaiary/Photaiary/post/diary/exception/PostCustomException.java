package com.photaiary.Photaiary.post.diary.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostCustomException extends Exception{
    public PostCustomException(String message) {
        super(message);
    }
}
