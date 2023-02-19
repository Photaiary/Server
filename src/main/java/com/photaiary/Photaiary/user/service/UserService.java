//package com.photaiary.Photaiary.user.service;
//
//import com.photaiary.Photaiary.user.entity.User;
//import com.photaiary.Photaiary.user.entity.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//
//
//    @Transactional
//    public int idChk(String nickname){
//        Optional<User> result = userRepository.findByNickname(nickname);
//            if (result.isPresent())
//                return 1;
//            else return 0;
//    }
//
//
//}
//
