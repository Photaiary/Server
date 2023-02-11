package com.photaiary.Photaiary.friend;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService service;

    @PostMapping("/friend/follow")
    public Map<Integer,Object> addFollow(@RequestBody FriendFollowRequestDto requestDto) {
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
        }

        data.put("isSuccess","true");
        response.put(HttpStatus.OK.value(),data);

        return response;
    }
}