package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class DiaryPostRequestDto {  //Controller와 Service 사이에서 사용할 Dto 클래스
    private String diaryTitle;
    private String diaryContent;
    private Daily daily;

    @Builder
    public DiaryPostRequestDto(String diaryTitle, String diaryContent, Daily daily) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;

        this.daily = daily;
    }

    public Diary toEntity(){ //간접적으로 Entity 테이블과 연결된 Posts의 생성자를 이용한 초기화를 진행한다.
        return Diary.builder()
                .diaryTitle(diaryTitle)
                .diaryContent(diaryContent)
                .daily(daily)
                .build();
    }
}

