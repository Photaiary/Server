package com.photaiary.Photaiary.user.dto;

import com.photaiary.Photaiary.user.entity.Authority;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.security.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponseDto{

//    private boolean isSuccess=true;

    private Long user_index;

    private String nickname;

    private String name;

    private String email;

    private List<Authority> roles = new ArrayList<>();

    private TokenDto token;

    public SignResponseDto(User user) {
        this.user_index= user.getUserIndex();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
