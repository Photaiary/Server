package com.photaiary.Photaiary.user;


import com.photaiary.Photaiary.global.DefaultRes;
import com.photaiary.Photaiary.user.dto.LoginDto;
import com.photaiary.Photaiary.user.dto.SignRequestDto;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.security.JwtAuthenticationFilter;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.security.TokenDto;
import com.photaiary.Photaiary.user.service.SignService;
import com.photaiary.Photaiary.user.validation.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @ApiOperation(value = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<DefaultRes> signin(@RequestBody LoginDto request) throws Exception { // SignResponseDto
        return new ResponseEntity<>(DefaultRes.res(signService.login(request)), HttpStatus.OK);

    }

    @ApiOperation(value = "회원 가입")
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
    public ResponseEntity<SignResponseDto> getUsger(@RequestBody Map<String,String> emailMap) throws Exception {
        return new ResponseEntity<>( signService.getUser(emailMap.get("email")), HttpStatus.OK);
    }

//    @GetMapping("/admin/get")
//    public ResponseEntity<SignResponseDto> getUserForAdmin(@RequestParam String account) throws Exception {
//        return new ResponseEntity<>( signService.getUser(account), HttpStatus.OK);
//    }


    @PostMapping("/duplicationCheck")
    public ResponseEntity<DefaultRes> idChk(@RequestBody Map<String,String> nicknameMap){
        String result=signService.idChk(nicknameMap.get("nickname"));
        return new ResponseEntity<>(DefaultRes.res(result), HttpStatus.OK);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity<DefaultRes> emailCheck(@RequestBody Map<String,String> emailMap) throws Exception {

        String authCode=emailService.sendEmail(emailMap.get("email"));

        return new ResponseEntity<>(DefaultRes.res(authCode),HttpStatus.OK);
    }

    @PostMapping("/update/theme")
    public ResponseEntity<DefaultRes> updateTheme(@RequestBody HashMap<String, String> themeMap)throws Exception
    {
        String theme=themeMap.get("theme");

        return new ResponseEntity<>(DefaultRes.res(signService.updateTheme(theme)),HttpStatus.OK);
    }

    @PostMapping("/update/nickname")
    public ResponseEntity<DefaultRes> updateNickname(@RequestBody HashMap<String, String> nicknameMap)throws Exception
    {
        String nickname=nicknameMap.get("nickname");

        return new ResponseEntity<>(DefaultRes.res(signService.updateNickname(nickname)),HttpStatus.OK);
    }

    @PostMapping("/update/name")
    public ResponseEntity<DefaultRes> updateName(@RequestBody HashMap<String, String> nameMap)throws Exception
    {
        String name=nameMap.get("name");

        return new ResponseEntity<>(DefaultRes.res(signService.updateName(name)),HttpStatus.OK);
    }
    @PostMapping("/update/password")
    public ResponseEntity<DefaultRes> updatePassword(@RequestBody HashMap<String, String> passwordMap)throws Exception
    {
        String password=passwordMap.get("password");

        return new ResponseEntity<>(DefaultRes.res(signService.updatePassword(password)),HttpStatus.OK);
    }


    @PostMapping("/update/birthdate")
    public ResponseEntity<DefaultRes> updateBirthdate(@RequestBody HashMap<String, String> birthdateMap)throws Exception
    {
        String birthdate=birthdateMap.get("birthdate");

        return new ResponseEntity<>(DefaultRes.res(signService.updateBirthdate(birthdate)),HttpStatus.OK);
    }

}