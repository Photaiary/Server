package com.photaiary.Photaiary.friend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFollowRequestDto {

    private String toUserEmail;
    private String fromUserToken;

    @Builder
    public FriendFollowRequestDto(String toUserEmail, String fromUserToken) {
        this.toUserEmail = toUserEmail;
        this.fromUserToken = fromUserToken;
    }
}

