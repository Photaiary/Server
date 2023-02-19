package com.photaiary.Photaiary.friend.entity.dto;

import com.photaiary.Photaiary.friend.entity.StatusType;
import com.photaiary.Photaiary.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class FriendFollowRequestDto {
    private User fromUser;
    private User toUser;
    private StatusType status;

    @Builder
    public FriendFollowRequestDto(User fromUser, User toUser, StatusType status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }
}
