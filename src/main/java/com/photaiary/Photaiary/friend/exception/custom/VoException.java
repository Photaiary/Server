package com.photaiary.Photaiary.friend.exception.custom;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VoException extends Exception{
    public VoException(String message) {
        super(message);
    }
}
