package com.photaiary.Photaiary.post.photo.controller.exception.custom;

import lombok.NoArgsConstructor;

import java.io.IOException;
@NoArgsConstructor
public class NoLocationException extends VoException {
    public NoLocationException(String message) {
        super(message);
    }
}
