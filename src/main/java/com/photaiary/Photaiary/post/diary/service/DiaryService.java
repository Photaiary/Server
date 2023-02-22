package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.diary.dto.DiaryPostRequestDto;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DailyReposiotry dailyReposiotry;
    private final UserRepository userRepository;
    //기능 개선: daily 정보가 없을 경우의 daily생성하는 기능
    public Diary save(DiaryPostRequestDto requestDto) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(auth.getName());
        if (!user.isPresent()) {
            throw new NoUserException("유효하지 않은 사용자 입니다.");
        }
        Optional<Daily> daily = dailyReposiotry.getDailyByUserAndValue(user.get(), requestDto.getDaily().getDailyValue());
        if (!daily.isPresent()) {
            Daily daily_new = Daily.builder()
                    .dailyValue(requestDto.getDaily().getDailyValue())
                    .user(user.get()).build();
            daily = Optional.of(dailyReposiotry.save(daily_new));
        }
    }

    @Transactional
    public Diary updateByIdAndDiaryIndex(Long dailyIndex, DiaryUpdateRequestDto requestDto) {
        Diary foundDiary = diaryRepository.findById(dailyIndex).get();

        if(foundDiary.getDiaryTitle() != null || foundDiary.getDiaryContent() != null) {
            foundDiary.update(requestDto.getDiaryTitle(),requestDto.getDiaryContent() );
            return foundDiary;
        }
        return null;
    }
    public List<Diary> findAll(){
        return repository.findAll();
    }
}
