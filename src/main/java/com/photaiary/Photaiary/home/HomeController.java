package com.photaiary.Photaiary.home;

import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.home.dto.GetHomeRes;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoUserException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags ="5. 홈 API")
public class HomeController {

    private final HomeService homeService;

    @ApiOperation(value = "홈 화면")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetHomeRes.class)})
    @GetMapping("/daily/{date}")  // /daily/20230222    -> 0222 ~ 0228
    public ResponseEntity<DefaultRes> getHome(@PathVariable String date) throws NoUserException {
        return new ResponseEntity<>(DefaultRes.res(homeService.getHome(date)), HttpStatus.OK);
    }

    @ApiOperation(value = "친구 화면")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetHomeRes.class)})
    @GetMapping("/daily/{userEmail}/{date}")
    public ResponseEntity<DefaultRes> getFriendHome(@PathVariable String userEmail,
                                                    @PathVariable String date) throws NoUserException {
        return new ResponseEntity<>(DefaultRes.res(homeService.getFriendHome(userEmail, date)), HttpStatus.OK);
    }

}