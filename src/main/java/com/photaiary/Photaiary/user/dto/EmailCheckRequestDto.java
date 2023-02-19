package com.photaiary.Photaiary.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCheckRequestDto {
    private String email;

    @Builder
    public EmailCheckRequestDto(String email){
        this.email=email;
    }
}