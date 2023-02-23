package com.photaiary.Photaiary.user.entity;

import com.photaiary.Photaiary.friend.entity.Friend;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.nickname FROM User u WHERE u.nickname LIKE :keyword")//🔨
    Optional<String> findByNicknameContaining(@Param("keyword") String keyword);//🔨
    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

}