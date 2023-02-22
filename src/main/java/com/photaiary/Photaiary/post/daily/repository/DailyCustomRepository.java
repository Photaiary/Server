package com.photaiary.Photaiary.post.daily.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.user.entity.User;

import java.util.Optional;

public interface DailyCustomRepository {
    Optional<Daily> getDailyByUserAndValue(User user, String dailyValue);
}
