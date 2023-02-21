package com.photaiary.Photaiary.post.daily.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.user.entity.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class DailyCustomRepositoryImpl implements DailyCustomRepository{
    private final EntityManager em;
    @Override
    public Optional<Daily> getDailyByUserAndValue(User user, String dailyValue) {
        String jpql = "SELECT d FROM Daily AS d WHERE d.user = :param1 AND d.dailyValue = :param2";
        TypedQuery<Daily> query = em.createQuery(jpql, Daily.class);
        query.setParameter("param1", user);
        query.setParameter("param2", dailyValue);
        List<Daily> dailies = query.getResultList();
        return dailies.stream().findAny();
    }
}
