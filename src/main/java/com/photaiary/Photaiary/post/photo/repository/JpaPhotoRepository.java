package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.user.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@NoArgsConstructor
@Repository
public abstract class JpaPhotoRepository implements PhotoRepository {
    private EntityManager em;
    @Override
    public Photo save(Photo photo) {
        em.persist(photo);
        return photo;
    }

    @Override
    public Optional<Photo> findById(Long id) {
        Photo photo = em.find(Photo.class, id);
        return Optional.ofNullable(photo);
    }

    public Optional<Photo> findByDaily(Daily daily) {
        Photo photo = em.find(Photo.class, daily);
        return Optional.ofNullable(photo);
    }
//
//    @Override
//    public Optional<Photo> findByDaily(Long dailyIndex) {
//        List<Photo> photos = em.createQuery("select p from Photo p where p.daily = :dailyIndex", Photo.class)
//                .setParameter("dailyIndex", dailyIndex)
//                .getResultList();
//        return photos.stream().findAny();
//    }
//
//    @Override
//    public Optional<Photo> findByUser(User user) {
//        // DailyRepository에서 User의 dailies를 가져온다.
//        // dailies index를 통해서 photos를 만든다.
//        return Optional.empty();
//    }
}
