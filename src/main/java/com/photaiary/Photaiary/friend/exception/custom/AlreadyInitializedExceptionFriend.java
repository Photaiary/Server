package com.photaiary.Photaiary.friend.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyInitializedExceptionFriend extends FriendCustomException {//👨‍💻
    public AlreadyInitializedExceptionFriend(String message) {
        super(message);
    }
}
