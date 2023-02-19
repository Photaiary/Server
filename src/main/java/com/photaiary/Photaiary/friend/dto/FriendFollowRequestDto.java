package com.photaiary.Photaiary.friend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFollowRequestDto {

    private String toUserEamil;
    private String fromUserToken;


    @Builder
    public FriendFollowRequestDto(String toUserEamil, String fromUserToken) {
        this.toUserEamil = toUserEamil;
        this.fromUserToken = fromUserToken;
    }
}

