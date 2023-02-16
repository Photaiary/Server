package com.photaiary.Photaiary.user.security;

import com.photaiary.Photaiary.user.entity.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key accessSecretKey;
    private Key refreshSecretKey;

    private final long accessTokenValidTime = 1000*60*30; //30분
    private final long refreshTokenValidTime=1000*60*60; //60분

    private final JpaUserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    protected void init() {
        accessSecretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
        refreshSecretKey= Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public TokenDto createToken(String email, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(email); //claim=payload 정보단위
        claims.put("roles", roles); //key,value 형태
        Date now = new Date();

        String accessToken= Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(accessSecretKey, SignatureAlgorithm.HS256)
                .compact();
        String refreshToken= Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(refreshSecretKey, SignatureAlgorithm.HS256)
                .compact();
        RefreshToken refreshTokenObj=RefreshToken.builder().refreshToken(refreshToken).keyEmail(email).build();
        refreshTokenRepository.save(refreshTokenObj);

        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }


    public TokenDto validateRefreshToken(String refreshToken)throws Exception{

        try {
            //검증
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성
            if (!claims.getBody().getExpiration().before(new Date())) {
                String newAccessToken= recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
                return TokenDto.builder().accessToken(newAccessToken).refreshToken(refreshToken).build();

            }
            }catch (Exception e){
                //refresh도 만료 됐을 시 재 로그인 필요
            throw new Exception("만료되었습니다. 다시 로그인을 해주세요");
            }

            return null;
        }

    public String recreationAccessToken(String email, Object roles){

        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저의 email 획득
    public String getEmail(String token) {
        // 만료된 토큰에 대해 parseClaimsJws를 수행하면 io.jsonwebtoken.ExpiredJwtException이 발생한다.
        try {
            Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return e.getClaims().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(token).getBody().getSubject();

    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}