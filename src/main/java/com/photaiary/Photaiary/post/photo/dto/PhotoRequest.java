package com.photaiary.Photaiary.post.photo.dto;

import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class PhotoRequest implements Serializable {
    private String dailyValue;
    private String comment = "";
    private String tagListString = "EMPTY";


    @Builder
    public PhotoRequest(String dailyValue, String comment, String tagListString) {
        this.dailyValue = dailyValue;
        this.comment = comment;
        this.tagListString = tagListString;
    }
}
