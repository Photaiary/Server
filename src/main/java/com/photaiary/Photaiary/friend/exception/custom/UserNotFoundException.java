package com.photaiary.Photaiary.friend.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends VoException{
    public UserNotFoundException(String message) {
        super(message);
    }
}

