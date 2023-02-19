package com.photaiary.Photaiary.friend;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.exception.custom.ToUserNotFoundException;
import com.photaiary.Photaiary.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

/**
 * 수정사항:
 * [Refactor] addFollow 구조 개선
 */
public class FriendController {

    private final FriendService service;

    @PostMapping("/friend/follow")
    public Map<Integer,Object> addFollow(@RequestBody FriendFollowRequestDto requestDto) throws Exception {
        Map<Integer, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        HttpStatus result = service.makeFriend(requestDto);

        if(result==HttpStatus.BAD_REQUEST){
            data.put("isSuccess","false");
            data.put("message","친구 요청이 실패하였습니다");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
        } else if (result==HttpStatus.NOT_FOUND) {
            data.put("isSuccess","false");
            data.put("message","친구가 존재하지 않습니다");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
        }else{
            data.put("isSuccess","true");
            response.put(HttpStatus.OK.value(),data);
        }

        return response;
    }

    @PostMapping("/friend/unfollow")
    public Map<Integer, Object> unFollow(@RequestBody FriendFollowRequestDto requestDto){
        Map<Integer, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        HttpStatus result = service.unFollow(requestDto);

        if(result==HttpStatus.BAD_REQUEST){
            data.put("isDelete","false");
            data.put("message","친구가 존재하지 않습니다");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
        } else if (result==HttpStatus.NOT_FOUND) {
            data.put("isDelete","false");
            data.put("message","친구 삭제 요청이 실패하였습니다");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
        }else{
            data.put("isDelete","true");
            response.put(HttpStatus.OK.value(),data);
        }
        return response;
    }

    @GetMapping("/friend/list/{userId}")
    public Map<Integer, Object> readFriends(@PathVariable Long userId){
        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        List<String> myFriends = service.readFriends(userId);

        if (myFriends == null) {
            data.put("isSuccess", "false");
            response.put(HttpStatus.BAD_REQUEST.value(), data);
        } else if (myFriends != null) {
            data.put("isSuccess","true");
            data.put("friend", myFriends);
            response.put(HttpStatus.OK.value(), data);
        }
        return response;
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
}
