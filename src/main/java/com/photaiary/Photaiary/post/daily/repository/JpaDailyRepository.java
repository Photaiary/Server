package com.photaiary.Photaiary.post.daily.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@NoArgsConstructor
@Repository
public abstract class JpaDailyRepository implements DailyReposiotry, DailyCustomRepository{
    private EntityManager em;

    @Override
    public Daily save(Daily daily) {
        em.persist(daily);
        return daily;
    }

    @Override
    public Optional<Daily> findById(Long id) {
        Daily daily = em.find(Daily.class, id);
        return Optional.ofNullable(daily);
    }
}
