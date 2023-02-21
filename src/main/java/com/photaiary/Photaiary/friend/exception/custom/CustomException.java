package com.photaiary.Photaiary.friend.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomException extends Exception{
    public CustomException(String message) {
        super(message);
    }
}
