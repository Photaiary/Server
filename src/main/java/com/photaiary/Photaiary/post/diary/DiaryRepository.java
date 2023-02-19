package com.photaiary.Photaiary.post.diary;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DiaryRepository extends JpaRepository<Diary,Long> {
}
