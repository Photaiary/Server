package com.photaiary.Photaiary.post.diary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiarySecretDto {
    private boolean isPublic = true;

    public DiarySecretDto(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
