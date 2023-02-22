package com.photaiary.Photaiary.post.diary.exception.custom;

import com.photaiary.Photaiary.post.diary.exception.PostCustomException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoUserException extends PostCustomException {
    public NoUserException(String message) {
        super(message);
    }
}
