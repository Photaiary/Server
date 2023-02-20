package com.photaiary.Photaiary.user;

import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.user.dto.EmailCheckResponseDto;
import com.photaiary.Photaiary.user.dto.ResponseDto;
import com.photaiary.Photaiary.user.dto.SignRequestDto;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.security.TokenDto;
import com.photaiary.Photaiary.user.service.SignService;
import com.photaiary.Photaiary.user.validation.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Api(tags ="1. 유저 관련 API")
public class UserController {


    private final EmailService emailService;

    private final SignService signService;

    private final JwtProvider jwtProvider;

//    private final UserRepository userRepository;


    @ApiOperation(value = "로그인")
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login")
    public ResponseEntity<DefaultRes> signin(@RequestBody SignRequestDto request) throws Exception { // SignResponseDto
        return new ResponseEntity<>(DefaultRes.res(signService.login(request)), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 가입")
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseEntity<DefaultRes> signup(@RequestBody SignRequestDto request) throws Exception {
        return new ResponseEntity<>(DefaultRes.res(signService.register(request)), HttpStatus.OK);
    }

    //refresh토큰으로 access토큰 재발급 요청
    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody HashMap<String, String> refreshTokenMap) throws Exception {
        return new ResponseEntity<>( signService.validateRefreshToken(refreshTokenMap.get("refreshToken")), HttpStatus.OK);
    }
    @PostMapping("/user/get")
    public ResponseEntity<SignResponseDto> getUser(@RequestBody Map<String,String> emailMap) throws Exception {
        return new ResponseEntity<>( signService.getUser(emailMap.get("email")), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponseDto> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( signService.getUser(account), HttpStatus.OK);
    }


    @PostMapping("/login/duplicationCheck")
    public ResponseDto idChk(@RequestBody Map<String,String> nicknameMap){
        int result=signService.idChk(nicknameMap.get("nickname"));
        if (result==0)
            return new ResponseDto(true);
        else return new ResponseDto(false);
    }

    @PostMapping("/emailCheck")
    public EmailCheckResponseDto emailCheck(@RequestBody Map<String,String> emailMap) throws Exception {

        String authCode=emailService.sendEmail(emailMap.get("email"));

        return new EmailCheckResponseDto(true,authCode);
    }



}