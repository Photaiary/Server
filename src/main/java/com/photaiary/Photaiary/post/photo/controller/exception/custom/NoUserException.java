package com.photaiary.Photaiary.post.photo.controller.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoUserException extends VoException{
    public NoUserException(String message) {
        super(message);
    }
}
