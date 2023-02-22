package com.photaiary.Photaiary.home.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private boolean isPublic;

    // 사진 (5개)
    private List<GetPhotoRes> photoList;

}
