package com.photaiary.Photaiary.home.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetPhotoRes {

    private Long id;
    private String latitude;
    private String longitude;
    private String image;
    private String comment;

    private List<String> tags;
}
