package com.photaiary.Photaiary.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IdCheckRequestDto {
    private String nickname;

    @Builder
    public IdCheckRequestDto(String nickname){
        this.nickname=nickname;
    }

}