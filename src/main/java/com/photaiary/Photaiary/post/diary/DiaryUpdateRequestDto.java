package com.photaiary.Photaiary.post.diary;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
