package com.photaiary.Photaiary.post.photo.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import com.photaiary.Photaiary.post.photo.dto.SinglePhotoDto;
import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.dto.SinglePhotoResponse;
import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import com.photaiary.Photaiary.post.photo.validator.PhotoRequestValidator;
import com.photaiary.Photaiary.post.photo.vo.BucketVo;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.utils.s3.S3UploadComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final DailyReposiotry dailyReposiotry;
    private final UserRepository userRepository;
    private final PhotoRequestValidator photoRequestValidator;
    private final S3UploadComponent s3UploadComponent;
    private final String ROOT = "/";

    @Transactional
    public String s3Upload(MultipartFile multipartFile) throws IOException {
        return s3UploadComponent.uploadOneFile(multipartFile, ROOT);
    }

    @Transactional
    public Long photoInfoSave(PhotoRequest photoRequest, PhotoVo photoVo, BucketVo bucketVo) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(auth.getName());
        if (!user.isPresent()) {
            throw new NoUserException("유효하지 않은 사용자 입니다.");
        }
        Optional<Daily> daily = dailyReposiotry.getDailyByUserAndValue(user.get(), photoRequest.getDailyValue());
        if (!daily.isPresent()) {
            Daily daily_new = Daily.builder().dailyValue(photoRequest.getDailyValue()).user(user.get()).build();
            daily = Optional.of(dailyReposiotry.save(daily_new));
        }
            Photo photo = new Photo().builder()
                    .comment(photoRequest.getComment())
                    .latitude(photoVo.getLatitude())
                    .longitude(photoVo.getLongitude())
                    .deleteStatus(DeleteStatus.exist)
                    .image(bucketVo.getImageLink())
                    .daily(daily.get())
                    .tag(photoRequestValidator.getStringTag(photoRequest.getTag()))
                    .build();
            return photoRepository.save(photo).getId();
    }

    @Transactional
    public Boolean photoEdit(EditRequest editRequest) throws VoException {
        Optional<Photo> photo = photoRepository.findById(editRequest.getPhotoIndex());
        try {
            if (photo.isPresent()) {
                photo.get().editTag(editRequest.getTag());
                photo.get().editComment(editRequest.getComment());
                photoRepository.save(photo.get());
                return photo.isPresent();
            }
            return false;
        } catch (Exception e) {
            throw new VoException("사진 파일이 존재하지 않습니다.");
        }
    }

    @Transactional
    public String getImage(Long uuid) throws VoException{
        Optional<Photo> photo = photoRepository.findById(uuid);
        try{
            if (photo.isPresent()) {
                return photo.get().getImage();
            } else if (!photo.isPresent()) {
                throw new VoException("사진파일이 존재하지 않습니다.1");
            }
            return "error";
        }
        catch (Exception e){
            throw new VoException("사진파일이 존재하지 않습니다.2");
        }
    }

    @Transactional
    public Boolean photoDelete(SinglePhotoDto singlePhotoDto) throws VoException  {
        try {
            return photoRepository.deleteByRequestId(singlePhotoDto.getPhotoIndex());
        } catch (Exception e) {
            throw new VoException("파일이 존재하지 않습니다");
        }
    }

    @Transactional
    public Optional<SinglePhotoResponse> viewSinglePhoto(SinglePhotoDto singlePhotoDto) throws VoException {
        try {
            Optional<Photo> photo = photoRepository.findById(singlePhotoDto.getPhotoIndex());
            SinglePhotoResponse singlePhotoResponse = SinglePhotoResponse.builder()
                    .imgLink(photo.get().getImage())
                    .latitude(photo.get().getLatitude())
                    .longitude(photo.get().getLongitude())
                    .build();
            return Optional.ofNullable(singlePhotoResponse);
        } catch (Exception e) {
            throw new VoException("사진이 존재하지 않습니다.");
        }
    }
//    @Transactional
//    public List<String> getImageByDaily(Long userId, String dailyValue) {
//        Daily today = dailyReposiotry.findByDailyValue  (dailyValue);
//        Optional<Photo> photo = photoRepository.findBy()
//    }
}
