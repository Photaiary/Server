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
    private Long dailyId;
    private String comment = "";
    private String tagListString = "EMPTY";


    @Builder
    public PhotoRequest(Long dailyId, String comment, String tagListString) {
        this.dailyId = dailyId;
        this.comment = comment;
        this.tagListString = tagListString;
    }
}
