package com.photaiary.Photaiary.user.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByEmail(String email);
}