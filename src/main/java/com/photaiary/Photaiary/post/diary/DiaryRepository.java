package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary,Long> {
    Optional<Diary> findByDaily(Daily daily);
}
