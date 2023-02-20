package com.photaiary.Photaiary.user;

import com.photaiary.Photaiary.user.dto.*;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.security.TokenDto;
import com.photaiary.Photaiary.user.service.SignService;
import com.photaiary.Photaiary.user.validation.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController {


    private final EmailService emailService;

    private final SignService signService;

    private final JwtProvider jwtProvider;

//    private final UserRepository userRepository;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login")
    public ResponseEntity<SignResponseDto> signin(@RequestBody LoginDto request) throws Exception {
        return new ResponseEntity<>(signService.login(request), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequestDto request) throws Exception {
        return new ResponseEntity<>(signService.register(request), HttpStatus.OK);
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

    @GetMapping("/api/profile")
    public ResponseEntity<SignResponseDto> getUserFromToken(HttpServletRequest request)throws Exception{
        String email=(String)request.getAttribute("username");
        return new ResponseEntity<>(signService.getUser(email),HttpStatus.OK);
    }



}