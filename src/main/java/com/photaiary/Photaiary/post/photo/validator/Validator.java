package com.photaiary.Photaiary.post.photo.validator;

public interface Validator {
    Boolean validatePhotoId(Long photoId);

    Boolean validateTag(String tag);
}
