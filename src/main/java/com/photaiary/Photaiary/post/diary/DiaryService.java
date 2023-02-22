package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository repository;
    private static List<Daily> dailies = new ArrayList<>();

    //기능 개선: daily 정보가 없을 경우의 daily생성하는 기능
    public Diary save(DiaryPostRequestDto requestDto) {
        Iterator<Daily> iterator = dailies.iterator();

        while(iterator.hasNext()){
            Daily daily = iterator.next();

            // CASE: exist daily
            if(daily.getDailyValue() == requestDto.getDaily().getDailyValue()){
                return repository.save(requestDto.toEntity());
            }

            // CASE: not exist daily
            // 요청받은 body 정보에서 daily에 대한 값을 적용하여 Daily 생성
            Daily.builder()
                .dailyValue(requestDto.getDaily().getDailyValue())
                .build();

            repository.save(requestDto.toEntity());
        }

        return repository.save(requestDto.toEntity());
    }

    @Transactional
    public Diary updateByIdAndDiaryIndex(Long dailyIndex, DiaryUpdateRequestDto requestDto) {
        Diary foundDiary = repository.findById(dailyIndex).get();

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
