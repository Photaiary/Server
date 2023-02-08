package com.photaiary.Photaiary.user.service;

import com.photaiary.Photaiary.user.dto.IdCheckRequestDto;
import com.photaiary.Photaiary.user.dto.UserSaveRequestDto;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public int idChk(String nickname){
        Optional<User> result = userRepository.findByNickname(nickname);
            if (result.isPresent())
                return 1;
            else return 0;
    }

    @Transactional
    public int save(UserSaveRequestDto requestDto){

       User result= userRepository.save(requestDto.toEntity());
       if(result.getNickname()==requestDto.toEntity().getNickname())
           return 0;
       else return 1;
    }

}

