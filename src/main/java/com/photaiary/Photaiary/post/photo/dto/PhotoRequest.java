package com.photaiary.Photaiary.post.photo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class PhotoRequest implements Serializable {
    private String dailyValue;
    private String comment = "";
    private String tag = "EMPTY";


    @Builder
    public PhotoRequest(String dailyValue, String comment, String tag) {
        this.dailyValue = dailyValue;
        this.comment = comment;
        this.tag = tag;
    }
}
