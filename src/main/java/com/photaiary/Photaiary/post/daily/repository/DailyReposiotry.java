package com.photaiary.Photaiary.post.daily.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyReposiotry extends JpaRepository<Daily, Long> {
}
