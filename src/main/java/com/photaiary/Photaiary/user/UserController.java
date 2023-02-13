package com.photaiary.Photaiary.user;

import com.photaiary.Photaiary.user.dto.EmailCheckResponseDto;
import com.photaiary.Photaiary.user.dto.ResponseDto;
import com.photaiary.Photaiary.user.dto.SignRequestDto;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.user.service.SignService;
import com.photaiary.Photaiary.user.validation.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController {


    private final EmailService emailService;

    private final SignService signService;

    private final UserRepository userRepository;



    @PostMapping(value = "/login")
    public ResponseEntity<SignResponseDto> signin(@RequestBody SignRequestDto request) throws Exception {
        return new ResponseEntity<>(signService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequestDto request) throws Exception {
        return new ResponseEntity<>(signService.register(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<SignResponseDto> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( signService.getUser(account), HttpStatus.OK);
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
