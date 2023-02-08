package com.photaiary.Photaiary.post.photo.dto;

import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class PhotoRequest {
    private Long dailyId;
    private String latitude;
    private String longitude;
    private String image;
    private String comment;
    private DeleteStatus deleteStatus;
    private String tag;

    @Builder
    public PhotoRequest(Long dailyId, String latitude, String longitude, String image, String comment, DeleteStatus deleteStatus, String tag) {
        this.dailyId = dailyId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.comment = comment;
        this.deleteStatus = deleteStatus;
        this.tag = tag;
    }
}
