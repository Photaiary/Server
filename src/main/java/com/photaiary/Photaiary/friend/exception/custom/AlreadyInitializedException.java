package com.photaiary.Photaiary.friend.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyInitializedException extends CustomException{//👨‍💻
    public AlreadyInitializedException(String message) {
        super(message);
    }
}
