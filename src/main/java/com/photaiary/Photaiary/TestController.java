package com.photaiary.Photaiary;

import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.user.exception.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@Api(tags ="Test API 입니다.")
public class TestController {

    @ApiOperation(value = "스웨거 테스트")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api")
    public ResponseEntity<DefaultRes> sayHello() {
        return new ResponseEntity<>(DefaultRes.res("Swagger Hello World!"), HttpStatus.OK);
    }

    @GetMapping("/test/docker")
    public String dockerTest(){
        return "진짜 마지막 도커 테스트";
    }

    @GetMapping("/test/webhook")
    public String webhookTest(){
        return "webhook";
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
