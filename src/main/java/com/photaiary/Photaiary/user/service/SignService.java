package com.photaiary.Photaiary.user.service;

import com.photaiary.Photaiary.user.dto.SignRequestDto;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.entity.Authority;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.security.Token;
import com.photaiary.Photaiary.user.security.TokenDto;
import com.photaiary.Photaiary.user.security.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final TokenRepository tokenRepository;

    public SignResponseDto login(SignRequestDto request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        user.setRefreshToken(createRefreshToken(user)); //이때 createrefreshtoken? 아니면

        return SignResponseDto.builder()
                .user_index(user.getUserIndex())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .roles(user.getRoles())
                .token(TokenDto.builder()
                        .access_token(jwtProvider.createToken(user.getEmail(),user.getRoles()))
                        .refresh_token(user.getRefreshToken()) //이때 createrefreshtoken?
                        .build())
                .build();

    }
    public boolean register(SignRequestDto request) throws Exception {
        try {
            User user = User.builder()
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .birthdate(request.getBirthdate())
                    .profileImage(request.getProfileImage())
                    .build();

            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public String createRefreshToken(User user) {
        Token token = tokenRepository.save(
                Token.builder()
                        .user_index(user.getUserIndex())
                        .refresh_token(UUID.randomUUID().toString())
                        .expiration(120) //만료시나리오 위해 2분
                        .build()
        );
        return token.getRefresh_token();
    }

    public Token validRefreshToken(User user, String refreshToken) throws Exception {
        Token token = tokenRepository.findByUserIndex(user.getUserIndex()).orElseThrow(() -> new Exception("만료된 계정입니다. 로그인을 다시 시도하세요"));
        // 해당유저의 Refresh 토큰 만료 : Redis에 해당 유저의 토큰이 존재하지 않음
        if (token.getRefresh_token() == null) {
            return null;
        } else {
            //리프레시토큰은 access재발급때 같이 재발급할건지 아니면 시간만 연장할건지
            //아니면 처음부터 14일씩 길게할건지. -> 이것도 만료다되가면 재발급?or 연장?
            // 리프레시 토큰 만료일자가 얼마 남지 않았을 때 만료시간 연장..?
            if (token.getExpiration() < 10) {
                token.setExpiration(1000);
                tokenRepository.save(token);
            }

            // 토큰이 같은지 비교
            if (!token.getRefresh_token().equals(refreshToken)) {
                return null;
            } else {
                return token;
            }
        }
    }

    public TokenDto refreshAccessToken(TokenDto token) throws Exception {
        String email = jwtProvider.getEmail(token.getAccess_token());
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));
        Token refreshToken = validRefreshToken(user, token.getRefresh_token());

        if (refreshToken != null) {
            return TokenDto.builder()
                    .access_token(jwtProvider.createToken(email, user.getRoles()))
                    .refresh_token(refreshToken.getRefresh_token())
                    .build();
        } else {
            throw new Exception("로그인을 해주세요");
        }
    }

    public SignResponseDto getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseDto(user);
    }

    @Transactional
    public int idChk(String nickname){
        Optional<User> result = userRepository.findByNickname(nickname);
        if (result.isPresent())
            return 1;
        else return 0;
    }


}