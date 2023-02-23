package com.photaiary.Photaiary.friend.entity.dto;

import com.photaiary.Photaiary.user.entity.Status;
import com.photaiary.Photaiary.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class FriendFollowRequestDto {
    private User fromUser;
    private User toUser;

    @Builder
    public FriendFollowRequestDto(User fromUser, User toUser, Status status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
