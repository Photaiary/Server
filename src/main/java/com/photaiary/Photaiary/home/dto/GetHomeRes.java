package com.photaiary.Photaiary.home.dto;

import com.photaiary.Photaiary.user.entity.User;
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

    public static GetHomeRes of(User user, List<GetDailyRes> getDailyResList) {
        return GetHomeRes.builder()
                .userIndex(user.getUserIndex())
                .getDailyResList(getDailyResList)
                .build();
    }
}
