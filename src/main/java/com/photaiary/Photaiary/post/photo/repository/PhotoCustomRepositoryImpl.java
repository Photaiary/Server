package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PhotoCustomRepositoryImpl implements PhotoCustomRepository {
    private final EntityManager em;

    @Override
    public Boolean deleteByRequestId(Long id) throws RuntimeException {
        Photo photo = em.find(Photo.class, id);
        if (photo != null) {
            Long result = photo.getId();
            em.remove(photo);
            return id == result;
        }
        throw new RuntimeException("Not Deleted");
    }

    @Override
    public Optional<Photo> getPhotoByDaily(Daily daily) {
        String jpql = "SELECT IMAGE FROM PHOTO WHERE daily_index = :param";
        TypedQuery<Photo> query = em.createQuery(jpql, Photo.class);
        query.setParameter("param", daily.getDailyIndex());

        Optional<List<Photo>> photos = Optional.ofNullable(query.getResultList());
        return Optional.empty();
    }
}
