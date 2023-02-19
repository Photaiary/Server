package com.photaiary.Photaiary.post.photo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EditRequest {
    Long photoIndex;
    String tag;
    String comment;

    @Builder
    public EditRequest(Long photoIndex, String tag, String comment) {
        this.photoIndex = photoIndex;
        this.tag = tag;
        this.comment = comment;
    }
}