package com.photaiary.Photaiary.home.dto;

import com.photaiary.Photaiary.post.photo.entity.Photo;
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

    public static GetPhotoRes of(Photo photo){
        return GetPhotoRes.builder()
                .id(photo.getId())
                .latitude(photo.getLatitude())
                .longitude(photo.getLongitude())
                .image(photo.getImage())
                .comment(photo.getComment())
                .build();
    }

}
