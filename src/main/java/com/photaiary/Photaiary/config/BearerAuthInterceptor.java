//package com.photaiary.Photaiary.config;
//
//import com.photaiary.Photaiary.user.security.JwtProvider;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class BearerAuthInterceptor implements HandlerInterceptor {
//    private AuthorizationExtractor authExtractor;
//    private JwtProvider jwtProvider;
//
//    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtProvider jwtProvider) {
//        this.authExtractor = authExtractor;
//        this.jwtProvider = jwtProvider;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response, Object handler) {
//        System.out.println(">>> interceptor.preHandle 호출");
//        String token = authExtractor.extract(request, "Bearer");
//        System.out.println(token);
//        System.out.println(jwtProvider.validateToken(token));
//        if (token == null || token.length() == 0) {
//            return true;
//        }
//
//        if (!jwtProvider.validateToken(token)) {
//            throw new IllegalArgumentException("유효하지 않은 토큰");
//        }
//
//        String username = jwtProvider.getEmail(token);
//        request.setAttribute("username", username);
//        return true;
//    }
//}
