package com.photaiary.Photaiary.friend;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.service.FriendService;
import com.photaiary.Photaiary.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Api(tags={"4.친구 관련 API"}, description = "FriendController")
@RestController
@RequiredArgsConstructor

/**
 * 수정사항:
 * [Refactor] addFollow 구조 개선
 */

public class FriendController {


    private final FriendService service;

    //@ApiOperation(value="친구 요청(팔로우)")
    @PostMapping("/friend/follow")//👨‍💻
    public ResponseEntity<Map<Integer, Object>> addFollow(@RequestBody FriendFollowRequestDto requestDto) throws Exception {
        Map<Integer, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        HttpStatus result = service.makeFriend(requestDto);

        data.put("isSuccess", "true");
        response.put(HttpStatus.OK.value(), data);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    //@ApiOperation(value="친구 삭제")
    @PostMapping("/friend/unfollow")

    public ResponseEntity<Map<Integer, Object>> unFollow(@RequestBody FriendFollowRequestDto requestDto) throws Exception {
        Map<Integer, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        HttpStatus result = service.unFollow(requestDto);

        if (result == HttpStatus.NOT_FOUND) {
            data.put("isDelete", "false");
            data.put("message", "친구가 존재하지 않습니다");
            response.put(HttpStatus.NOT_FOUND.value(), data);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (result == HttpStatus.BAD_REQUEST) {
            data.put("isDelete", "false");
            data.put("message", "친구 삭제 요청이 실패하였습니다");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            data.put("isDelete", "true");
            response.put(HttpStatus.OK.value(), data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    //@ApiOperation(value="친구 목록 보기")
    @GetMapping("/friend/list/{userId}")

    public ResponseEntity<Map<Integer, Object>> readFriends(@PathVariable String userId) { //userId is token
        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        List<String> myFriends = service.readFriends(userId);

        if (myFriends == null) {
            data.put("isSuccess", "false");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (myFriends != null) {
            data.put("isSuccess", "true");
            data.put("friend", myFriends);
            response.put(HttpStatus.OK.value(), data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return null;
    }
}
    //예외 처리 -> 컨트롤러 및 서비스계층에서 하지않고 throws Exception을 통해서 조금 더 객체적으로 설계해보기.
    //->컨트롤러 계층에서 예외 처리
    //->서비스 계층에서 예외 처리

    /**
     * 참고만 하자.(photo 컨트롤러)
     */
//    @PostMapping("/uploadtest")
//    public Long uploadTest(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
//        PhotoVo photoVo = new PhotoVo(img);
//        PhotoS3Dto photoS3Dto = new PhotoS3Dto();
//        if (photoVo.hasLocation()) {
//            return photoService.photoInfoSave(photoRequest, photoVo, photoS3Dto);
//        }
//        return -100L;
//    }
//
//    @PostMapping("/uploadtest")
//    public Long uploadTest(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
//        PhotoVo photoVo = new PhotoVo(img);
//        PhotoS3Dto photoS3Dto = new PhotoS3Dto();
//        return photoService.photoInfoSave(photoRequest, photoVo, photoS3Dto);
//    }

