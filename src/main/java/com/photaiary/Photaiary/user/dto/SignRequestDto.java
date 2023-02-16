package com.photaiary.Photaiary.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequestDto {

    private Long user_index;

    private String email;

    private String password;

    private String nickname;

    private String name;

    private String birthdate;

    private String profileImage;




}