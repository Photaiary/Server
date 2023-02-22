package com.photaiary.Photaiary.home;

import com.photaiary.Photaiary.home.dto.GetDailyRes;
import com.photaiary.Photaiary.home.dto.GetHomeRes;
import com.photaiary.Photaiary.home.dto.GetPhotoRes;
import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.diary.Diary;
import com.photaiary.Photaiary.post.diary.DiaryRepository;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final DailyReposiotry dailyReposiotry; // 날짜
    private final DiaryRepository diaryRepository; // 게시물
    private final PhotoRepository photoRepository; // 사진

    private final UserRepository userRepository;

    @Transactional
    public GetHomeRes getHome(String date) throws ParseException, NoUserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(auth.getName());

        if (!user.isPresent()) {
            throw new NoUserException("유효하지 않은 사용자 입니다.");
        }

        List<GetDailyRes> getDailyResList = getDailyResList(user.get(), date);
        return homeResponse(user.get(), getDailyResList);
    }

    @Transactional
    public GetHomeRes getFriendHome(String userNickname, String date) throws NoUserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(auth.getName());

        if (!user.isPresent()) {
            throw new NoUserException("유효하지 않은 사용자 입니다.");
        }

        Optional<User> friend = userRepository.findByNickname(userNickname);
        if(friend.isEmpty()){
            throw new NoUserException("존재하지 않은 사용자 입니다.");
        }

        List<GetDailyRes> getDailyResList = getDailyResList(friend.get(), date);
        return homeResponse(friend.get(), getDailyResList);
    }

    private GetHomeRes homeResponse(User user, List<GetDailyRes> getDailyResList){
        return GetHomeRes.builder()
                .userIndex(user.getUserIndex())
                .getDailyResList(getDailyResList)
                .build();
    }


    private List<GetDailyRes> getDailyResList(User user, String date) {
        List<GetDailyRes> getDailyResList = new ArrayList<>();
        // date를 기준으로 일주일 치의 정보를 가지고 와야함
        for (int i = 0; i < 7; i++) {
            // 1) 마지막날 계산 : 28, 30, 31

            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));

            localDate = localDate.plusDays(i);
            String getDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 만약 값이 없으면?
            Optional<Daily> daily = dailyReposiotry.getDailyByUserAndValue(user, getDate);
            List<GetPhotoRes> photoListRes = new ArrayList<>();

            Daily getDaily;
            if(daily.isEmpty()){
                Daily createDaily = Daily.builder()
                        .user(user)
                        .dailyValue(getDate)
                        .build();
                Daily saveDaily = dailyReposiotry.save(createDaily);
                getDaily = saveDaily;
            }else{
                getDaily = daily.get();
            }

            Optional<Diary> diary = diaryRepository.findByDaily(getDaily);

            Diary getDiary;
            if(diary.isEmpty()){
                Diary createDiary = Diary.builder()
                        .daily(getDaily)
                        .build();
                Diary saveDiary = diaryRepository.save(createDiary);
                getDiary = saveDiary;
            }else{
                getDiary = diary.get();
            }

            // 사진 정보 가져오기
            List<Photo> photoList = photoRepository.findAllByDaily(getDaily);

            if (photoList.size() != 0) {
                for (Photo getPhoto : photoList) {

                    // 태그 처리
                    GetPhotoRes getPhotoRes = GetPhotoRes.builder()
                            .id(getPhoto.getId())
                            .latitude(getPhoto.getLatitude())
                            .longitude(getPhoto.getLongitude())
                            .image(getPhoto.getImage())
                            .comment(getPhoto.getComment())
                            .build();

                    photoListRes.add(getPhotoRes);
                }

            }

            GetDailyRes getDailyRes = GetDailyRes.builder()
                    .dailyIndex(getDaily.getDailyIndex())
                    .date(getDate)
                    .diaryTitle(getDiary.getDiaryTitle())
                    .diaryContent(getDiary.getDiaryContent())
                    .isPublic(true) // 추가 필요함
                    .photoList(photoListRes)
                    .build();

            getDailyResList.add(getDailyRes);
        }
        return getDailyResList;
    }

}
