package com.photaiary.Photaiary;

import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class TestController {

    @GetMapping("/test/docker")
    public String dockerTest(){
        return "hi";
    }

    @GetMapping("/test/{hi}")
    public ResponseEntity<DefaultRes> test(@PathVariable String hi) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Test입니다.");

        if(hi.equals("hi")){
            throw new UserNotFoundException(String.format("ID[%s] not found",hi));
        }

        return new ResponseEntity<>(DefaultRes.res(map), HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login() {
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", 200);
//        response.put("message", "로그인 성공");
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWRIjoib3VyLXNvcHQbpXkxZgFXHw");
//        response.put("data", data);
//
//        return ResponseEntity.ok(response);
//    }

}
