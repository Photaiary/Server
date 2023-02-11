package com.photaiary.Photaiary.user;

import com.photaiary.Photaiary.user.dto.*;
import com.photaiary.Photaiary.user.service.UserService;
import com.photaiary.Photaiary.user.validation.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.config.annotation.authentication
        .builders.AuthenticationManagerBuilder;

import javax.validation.Valid;
import java.util.Map;


@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;



    @PostMapping("/user/signup")
    public ResponseDto signup(@RequestBody UserSaveRequestDto requestDto){
        int result=userService.save(requestDto);
        if (result==0)
            return new ResponseDto(true);
        else return new ResponseDto(false);
    }

    @PostMapping("/user/login/duplicationCheck")
    public ResponseDto idChk(@RequestBody Map<String,String> nicknameMap){
        int result=userService.idChk(nicknameMap.get("nickname"));
        if (result==0)
            return new ResponseDto(true);
        else return new ResponseDto(false);
    }

    @PostMapping("/user/emailCheck")
    public EmailCheckResponseDto emailCheck(@RequestBody Map<String,String> emailMap) throws Exception {

        String authCode=emailService.sendEmail(emailMap.get("email"));

        return new EmailCheckResponseDto(true,authCode);
    }



}
