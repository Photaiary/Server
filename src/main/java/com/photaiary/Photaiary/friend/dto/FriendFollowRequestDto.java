package com.photaiary.Photaiary.friend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFollowRequestDto {

    private Long toUserId;
    private Long fromUserId;
    private Long asfafsds;

    @Builder
    public FriendFollowRequestDto(Long toUserId, Long fromUserId) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
