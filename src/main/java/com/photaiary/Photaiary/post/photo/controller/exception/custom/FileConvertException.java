package com.photaiary.Photaiary.post.photo.controller.exception.custom;

import lombok.NoArgsConstructor;

import java.io.IOException;
@NoArgsConstructor
public class FileConvertException extends VoException {
    public FileConvertException(String message) {
        super(message);
    }
}
