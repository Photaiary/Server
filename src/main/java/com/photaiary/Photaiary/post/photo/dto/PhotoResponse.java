package com.photaiary.Photaiary.post.photo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PhotoResponse {
    private Long photoIndex;
    private String latitude;
    private String longitude;

    @Builder
    public PhotoResponse(Long photoIndex, String latitude, String longitude) {
        this.photoIndex = photoIndex;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
