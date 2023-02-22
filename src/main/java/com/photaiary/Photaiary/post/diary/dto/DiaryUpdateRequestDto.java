package com.photaiary.Photaiary.post.diary.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryUpdateRequestDto {
    private String diaryTitle;
    private String diaryContent;

    @Builder
    public DiaryUpdateRequestDto(String diaryTitle, String diaryContent) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }
}
