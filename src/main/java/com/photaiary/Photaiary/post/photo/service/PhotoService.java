package com.photaiary.Photaiary.post.photo.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    @Transactional
    public Long photoSave(PhotoRequest photoRequest) {
//        Optional<Daily>daily = jpaDailyRepository.findById(photoRequest.getDailyId());
        Optional<Daily> daily = Optional.of(new Daily()
                .builder()
                .dailyIndex(photoRequest.getDailyId())
                .build());
        try {
            if (daily.isPresent()) {
                Photo photo = new Photo()
                        .builder()
                        .latitude(photoRequest.getLatitude())
                        .longitude(photoRequest.getLongitude())
                        .image(photoRequest.getImage())
                        .comment(photoRequest.getComment())
                        .deleteStatus(photoRequest.getDeleteStatus())
                        .tag(photoRequest.getTag())
                        .daily(daily.get())
                        .build();
                return photoRepository.save(photo).getId();
            } else if (daily.isEmpty()) {
                System.out.println("empty");
                return -1000L;
            } else {
                return -999L;
            }
        } catch (Exception e) {
            System.out.println(e);
            return 11111111111111L;
        }
    }
}
