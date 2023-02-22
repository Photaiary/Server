package com.photaiary.Photaiary.user.service;

import com.photaiary.Photaiary.user.dto.LoginDto;
import com.photaiary.Photaiary.user.dto.SignRequestDto;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.entity.Authority;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.user.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JpaUserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public TokenDto login(LoginDto request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        //이미 존재하던 refreshtoken 삭제.
        if (refreshTokenRepository.existsByKeyEmail(request.getEmail())) {
            refreshTokenRepository.deleteByKeyEmail(request.getEmail());
        }
        TokenDto tokenDto = jwtProvider.createToken(user.getEmail(), user.getRoles());
        SignResponseDto signResponseDto = SignResponseDto.builder()
                .user_index(user.getUserIndex())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .roles(user.getRoles())
                .token(TokenDto.builder()
                        .accessToken(tokenDto.getAccessToken())
                        .refreshToken(tokenDto.getRefreshToken())
                        .build())
                .build();

        return signResponseDto.getToken();
    }

    public boolean logout() throws Exception{


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Optional<User> findUser = userRepository.findByEmail(auth.getName());
        User user=findUser.orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));

        refreshTokenRepository.deleteByKeyEmail(user.getEmail());
        return true;
    }

    public String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }
    public TokenDto validateRefreshToken(String refreshToken) throws Exception {
        RefreshToken refreshToken1 = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() ->
                new BadCredentialsException("refreshToken 찾을 수 없음"));
        return jwtProvider.validateRefreshToken(refreshToken);
    }

    public boolean register(SignRequestDto request) throws Exception {
        try {
            User user = User.builder()
                    .password(passwordEncoding(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .birthdate(request.getBirthdate())
                    .build();

            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public SignResponseDto getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseDto(user);
    }

    @Transactional
    public String idChk(String nickname) {
        Optional<User> result = userRepository.findByNickname(nickname);
        if (result.isPresent())
            return "이미 사용 중";
        else return "사용 가능";
    }

    @Transactional
    public String[] updateTheme(String theme) throws Exception {
        String email = jwtAuthenticationFilter.getEmail();
        System.out.println("service에서 보내는 이메일 정보=" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        System.out.println("service에서 보내는 theme정보=" + theme);

        return user.updateTheme(theme);
    }

    @Transactional
    public String updateNickname(String nickname) throws Exception {

        String res = this.idChk(nickname);

        if (res.equals("사용 가능")) {
            String email = jwtAuthenticationFilter.getEmail();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
            return user.updateNickname(nickname);

        } else if (res.equals("이미 사용 중"))
            return ("이미 사용 중인 아이디");
        else throw new Exception("변경 실패");
    }
    @Transactional
    public boolean updateBirthdate(String birthdate) throws Exception {
        String email = jwtAuthenticationFilter.getEmail();
//        System.out.println("service에서 보내는 이메일 정보=" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));

        return user.updateBirthdate(birthdate);
    }
    @Transactional
    public boolean updateName(String name) throws Exception {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<User> user = userRepository.findByEmail(auth.getName());
//
        String email = jwtAuthenticationFilter.getEmail();
//        System.out.println("service에서 보내는 이메일 정보=" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));

        return user.updateName(name);
    }

    @Transactional
    public boolean updatePassword(String password) throws Exception {
        String email = jwtAuthenticationFilter.getEmail();
//        System.out.println("service에서 보내는 이메일 정보=" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        String encodedPassword=this.passwordEncoding(password);
        return user.updatePassword(encodedPassword);
    }


    @Transactional
    public boolean withdraw(LoginDto request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

       userRepository.delete(user);
        return true;

    }


}


//    public String createRefreshToken(User user) {
//        Token token =
//                Token.builder()
//                        .userIndex(user.getUserIndex())
//                        .email(user.getEmail())
//                        .refreshToken(UUID.randomUUID().toString())
//                        .expiration(2400)
//                        .build();
//        tokenRepository.save(token);
//
//
//        return token.getRefreshToken();
//    }

//    public Token validRefreshToken(User user, Token token) throws Exception {
////        Token token = tokenRepository.findByEmail(user.getEmail()).orElseThrow(() -> new Exception("만료된 계정입니다. 로그인을 다시 시도하세요"));
//        // 해당유저의 Refresh 토큰 만료 : Redis 에 해당 유저의 토큰이 존재하지 않음
//        if (token.getRefresh_token() == null) {
//            return null;
//        } else {
//            //리프레시토큰은 access 재발급 때 같이 재발급할건지 아니면 시간만 연장할건지
//            //아니면 처음부터 14일씩 길게할건지. -> 이것도 만료다되가면 재발급?or 연장?
//            //리프레시 토큰 만료일자가 얼마 남지 않았을 때 만료시간 연장..?
//            if (token.getExpiration() < 10) {
//                token.setExpiration(1000);
//                tokenRepository.save(token);
//
//
//            }
//
//            // 토큰이 같은지 비교
////            if (!token.getRefresh_token().equals(refreshToken)) {
////                return null;
////            } else {
////                return token;
////            }
//        }
//    }

//    public TokenDto refreshAccessToken(TokenDto tokendto) throws Exception {
////        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getEmail(token.getAccess_token()));
//
////        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getEmail(token.getAccess_token()));
////
////        String email = userDetails.getUsername();
////        User user = userRepository.findByEmail(email).orElseThrow(() ->
////                new BadCredentialsException("잘못된 계정정보입니다."));
//        String refreshToken=tokendto.getRefresh_token();
//        Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new Exception("만료된 계정입니다. 로그인을 다시 시도하세요"));
//        User user = userRepository.findByRefreshToken(token.getRefreshToken()).orElseThrow(() ->
//                new BadCredentialsException("잘못된 계정정보입니다."));
////        Token refreshToken = validRefreshToken(user, token);
//
//        if (refreshToken != null) {
//            return TokenDto.builder()
//                    .access_token(jwtProvider.createToken(user.getEmail(), user.getRoles()))
//                    .refresh_token(refreshToken)
//                    .build();
//        } else {
//            throw new Exception("로그인을 해주세요");
//        }
//    }