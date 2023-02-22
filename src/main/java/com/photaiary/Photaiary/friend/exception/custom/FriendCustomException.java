package com.photaiary.Photaiary.friend.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendCustomException extends Exception{//👨‍💻
    public FriendCustomException(String message) {
        super(message);
    }
}

