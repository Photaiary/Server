package com.photaiary.Photaiary.post.photo.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.photo.dto.EditRequest;
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
    private final DailyReposiotry dailyReposiotry;

    @Transactional
    public Long photoSave(PhotoRequest photoRequest) {
        Optional<Daily> daily = dailyReposiotry.findById(photoRequest.getDailyId());
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
                return -900L;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -800L;
        }
    }

    @Transactional
    public Long photoEdit(EditRequest editRequest) {
        Optional<Photo> photo = photoRepository.findById(editRequest.getPhotoIndex());
        try {
            if (photo.isPresent()) {
                photo.get().editTag(editRequest.getTag());
                photo.get().editComment(editRequest.getComment());
                photoRepository.save(photo.get());
                return photo.get().getId();
            } else if (photo.isEmpty()) {
                return -1000L;
            } else {
                return -900L;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -800L;
        }
    }

//    private Map<String, String> photoLocation() {
//
//    }
}
