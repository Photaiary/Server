package com.photaiary.Photaiary.home;

import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@Api(tags ="5. 홈 API")
public class HomeController {

    private final HomeService homeService;

    // 시작 날짜 -> 일주일 치 정보 보여 주기
    // 완전 처음 홈  (일주일 - 다이어리 정보 x)  // 0208~2014
    @GetMapping("/daily/{date}")  // /daily/20230222    -> 0222 ~ 0228
    public ResponseEntity<DefaultRes> getHome(@PathVariable String date) throws ParseException, NoUserException {
        return new ResponseEntity<>(DefaultRes.res(homeService.getHome(date)), HttpStatus.OK);
    }

    // /diary/{diaryId}

    // 친구 홈 : 친구쪽 가면 확인 유무
    @GetMapping("/daily/{userNickname}/{date}")
    public ResponseEntity<DefaultRes> getFriendHome(@PathVariable String userNickname,
                                                    @PathVariable String date) throws NoUserException {
        return new ResponseEntity<>(DefaultRes.res(homeService.getFriendHome(userNickname, date)), HttpStatus.OK);
    }


}