package com.photaiary.Photaiary.home.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetHomeRes {
    // 유저 정보
    private Long userIndex;

    // daily 정보
    private List<GetDailyRes> getDailyResList;

}
