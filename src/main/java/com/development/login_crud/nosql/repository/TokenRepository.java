package com.development.login_crud.nosql.repository;

import com.development.login_crud.nosql.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findAllByUser_IdAndRevokedIsFalseOrExpiredIsFalse(String userId);}

