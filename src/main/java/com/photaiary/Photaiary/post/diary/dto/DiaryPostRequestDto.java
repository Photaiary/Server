package com.photaiary.Photaiary.post.diary.dto;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.diary.Diary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryPostRequestDto {  //Controller와 Service 사이에서 사용할 Dto 클래스
    private String diaryTitle;
    private String diaryContent;
    private String dailyValue;

    @Builder
    public DiaryPostRequestDto(String diaryTitle, String diaryContent, String dailyValue) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.dailyValue = dailyValue;
    }
}

