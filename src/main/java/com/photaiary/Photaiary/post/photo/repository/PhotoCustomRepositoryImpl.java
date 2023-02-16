package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.photo.entity.Photo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class CustomPhotoRepositoryImpl implements PhotoCustomRepository {
    private final EntityManager em;
    @Override
    public Boolean deleteByRequestId(Long id) throws RuntimeException{
        Photo photo = em.find(Photo.class, id);
        if (photo != null) {
            Long result = photo.getId();
            em.remove(photo);
            return id==result;
        }
        throw new RuntimeException("Not Deleted");
    }
}
