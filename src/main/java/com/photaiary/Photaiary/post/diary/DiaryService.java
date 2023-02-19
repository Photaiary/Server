package com.photaiary.Photaiary.post.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository repository;

    public Diary save(DiaryPostRequestDto requestDto) {
        return repository.save(requestDto.toEntity());
    }
    public List<Diary> findAll(){
        return repository.findAll();
    }
}
