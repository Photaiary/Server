package com.photaiary.Photaiary.post.photo.controller.exception.custom;

import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class VoException extends Exception{
    public VoException(String message) {
        super(message);
    }
}
