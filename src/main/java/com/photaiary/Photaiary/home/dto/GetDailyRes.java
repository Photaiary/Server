package com.photaiary.Photaiary.home.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.diary.Diary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetDailyRes {  // 하루하루의 정보

    // 날짜
    private Long dailyIndex;
    private String date;

    // 게시물
    private String diaryTitle;
    private String diaryContent;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    // 사진 (5개)
    private List<GetPhotoRes> photoList;

    public static GetDailyRes of(String getDate, Daily getDaily, Diary getDiary, List<GetPhotoRes> photoListRes){
        return GetDailyRes.builder()
                .dailyIndex(getDaily.getDailyIndex())
                .date(getDate)
                .diaryTitle(getDiary.getDiaryTitle())
                .diaryContent(getDiary.getDiaryContent())
                .isPublic(getDiary.isPublic()) // 추가 필요함
                .photoList(photoListRes)
                .build();
    }

    public static GetDailyRes ofFalseDiary(String getDate, Daily getDaily, Diary getDiary, List<GetPhotoRes> photoListRes){
        return GetDailyRes.builder()
                .dailyIndex(getDaily.getDailyIndex())
                .date(getDate)
                .diaryTitle(null)
                .diaryContent(null)
                .isPublic(getDiary.isPublic()) // 추가 필요함
                .photoList(photoListRes)
                .build();
    }

}
