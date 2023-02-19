package com.photaiary.Photaiary.user.dto;

import com.photaiary.Photaiary.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthdate;
    private String profileImage;

    @Builder
    public UserSaveRequestDto(String email, String password,String name,String nickname
            ,String birthdate,String profileImage) {
        this.email = email;
        this.password=password;
        this.name = name;
        this.nickname=nickname;
        this.birthdate=birthdate;
        this.profileImage=profileImage;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .birthdate(birthdate)
                .profileImage(profileImage)
                .build();
    }
}