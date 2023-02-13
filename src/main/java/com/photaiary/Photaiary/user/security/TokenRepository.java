package com.photaiary.Photaiary.user.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByUserIndex(Long aLong);
}
