package com.photaiary.Photaiary.user.entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsernmae(String username);
}
