//package com.photaiary.Photaiary.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMVCConfig implements WebMvcConfigurer {
//    private final BearerAuthInterceptor bearerAuthInterceptor;
//
//    public WebMVCConfig(BearerAuthInterceptor bearerAuthInterceptor) {
//        this.bearerAuthInterceptor = bearerAuthInterceptor;
//    }
//
//    public void addInterceptors(InterceptorRegistry registry){
//        System.out.println(">>> 인터셉터 등록");
//        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/api/profile");
//        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/api/profile/update");
//        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/api/boards");
//    }
//}