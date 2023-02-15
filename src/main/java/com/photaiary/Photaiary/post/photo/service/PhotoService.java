package com.photaiary.Photaiary.post.photo.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoS3Dto;
import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import com.photaiary.Photaiary.post.photo.validator.PhotoRequestValidator;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final DailyReposiotry dailyReposiotry;
    private final PhotoRequestValidator photoRequestValidator;

    @Transactional
    public Long photoInfoSave(PhotoRequest photoRequest, PhotoVo photoVo, PhotoS3Dto photoS3Dto) throws Exception {
        Optional<Daily> daily = dailyReposiotry.findById(photoRequest.getDailyId());
        if (daily.isPresent()) {
            Photo photo = new Photo().builder()
                    .comment(photoRequest.getComment())
                    .latitude(photoVo.getLatitude())
                    .longitude(photoVo.getLongitude())
                    .deleteStatus(DeleteStatus.exist)
                    // image Location을 PhotoS3Dto에서 받아와야한다.
                    .image("location")
                    .daily(daily.get())
                    .tag(photoRequestValidator.getStringTag(photoRequest.getTagListString()))
                    .build();
            return photoRepository.save(photo).getId();
        } else if (daily.isEmpty()) {
            throw new NoUserException("nono");
//            return -700L;
        }
        return -800L;
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

    @Transactional
    public String getImage(Long uuid) {
        Optional<Photo> photo = photoRepository.findById(uuid);
        if (photo.isPresent()) {
            return photo.get().getImage();
        } else if (!photo.isPresent()) {
            return "noPhoto";
        }
        return "error";
    }

//    @Transactional
//    public List<String> getImageByDaily(Long userId, String dailyValue) {
//        Daily today = dailyReposiotry.findByDailyValue  (dailyValue);
//        Optional<Photo> photo = photoRepository.findBy()
//    }
}
