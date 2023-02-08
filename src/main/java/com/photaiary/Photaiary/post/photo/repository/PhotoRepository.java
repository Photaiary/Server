package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo save(Photo photo);

    Optional<Photo> findById(Long id);
}
