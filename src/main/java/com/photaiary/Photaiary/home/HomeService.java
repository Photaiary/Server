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
    public GetHomeRes getHome(String date) throws NoUserException {
        Optional<User> user = checkUserAuth();
        List<GetDailyRes> getDailyResList = getDailyResList(user.get(), date);
        return GetHomeRes.of(user.get(), getDailyResList);
    }

    @Transactional
    public GetHomeRes getFriendHome(String userNickname, String date) throws NoUserException {
        Optional<User> user = checkUserAuth();
        Optional<User> friend = userRepository.findByNickname(userNickname);
        if(friend.isEmpty()){
            throw new NoUserException("존재하지 않은 사용자 입니다.");
        }
        List<GetDailyRes> getDailyResList = getFriendDailyResList(friend.get(), date);
        return GetHomeRes.of(friend.get(), getDailyResList);
    }

    private Optional<User> checkUserAuth() throws NoUserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(auth.getName());
        if (!user.isPresent()) {
            throw new NoUserException("유효하지 않은 사용자 입니다.");
        }
        return user;
    }

    private List<GetDailyRes> getDailyResList(User user, String date) {
        List<GetDailyRes> getDailyResList = new ArrayList<>();
        // date를 기준으로 일주일 치의 정보를 가지고 와야함
        for (int i = 0; i < 7; i++) {
            GetDailyRes getDailyRes = getDailyRes(user, date, i);
            getDailyResList.add(getDailyRes);
        }
        return getDailyResList;
    }

    private List<GetDailyRes> getFriendDailyResList(User user, String date) {
        List<GetDailyRes> getDailyResList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            GetDailyRes getDailyRes = getDailyRes(user, date, i);
            if(!getDailyRes.getIsPublic()){
                getDailyRes.changeFrinedDiaryInfo();
            }
            getDailyResList.add(getDailyRes);
        }
        return getDailyResList;
    }

    private GetDailyRes getDailyRes(User user, String date, int i) {
        // 날짜 얻기
        String getDate = getDate(date, i);

        // daily (날짜)
        Optional<Daily> daily = dailyReposiotry.getDailyByUserAndValue(user, getDate);
        Daily getDaily = checkDaily(daily, user, getDate);

        // diary (게시글)
        Optional<Diary> diary = diaryRepository.findByDaily(getDaily);
        Diary getDiary = checkDiary(diary, getDaily);

        // photoList  - 할일 1) 태그 처리 하기
        List<Photo> photoList = photoRepository.findAllByDaily(getDaily);
        List<GetPhotoRes> photoListRes = checkPhotoList(photoList);

        return GetDailyRes.of(getDate, getDaily, getDiary, photoListRes);
    }

    private String getDate(String date, int cnt) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        localDate = localDate.plusDays(cnt);
        String getDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return getDate;
    }

    private Daily checkDaily(Optional<Daily> daily, User user, String getDate) {
        Daily getDaily;
        if(daily.isEmpty()){
            Daily createDaily = Daily.toEntity(user, getDate);
            Daily saveDaily = dailyReposiotry.save(createDaily);
            getDaily = saveDaily;
        }else{
            getDaily = daily.get();
        }
        return getDaily;
    }

    private Diary checkDiary(Optional<Diary> diary, Daily getDaily) {
        Diary getDiary;
        if(diary.isEmpty()){
            Diary createDiary = Diary.toEntity(getDaily);
            Diary saveDiary = diaryRepository.save(createDiary);
            getDiary = saveDiary;
        }else{
            getDiary = diary.get();
        }
        return getDiary;
    }

    private List<GetPhotoRes> checkPhotoList(List<Photo> photoList) {
        List<GetPhotoRes> photoListRes = new ArrayList<>();
        if (photoList.size() != 0) {
            for (Photo getPhoto : photoList) {
                GetPhotoRes getPhotoRes = GetPhotoRes.of(getPhoto);
                photoListRes.add(getPhotoRes);
            }
        }
        return photoListRes;
    }

}
