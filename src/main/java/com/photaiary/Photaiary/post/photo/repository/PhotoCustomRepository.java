package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.entity.Photo;

import java.util.Optional;

public interface PhotoCustomRepository {
    Boolean deleteByRequestId(Long id);

    Optional<Photo> getPhotoByDaily(Daily daily);
}