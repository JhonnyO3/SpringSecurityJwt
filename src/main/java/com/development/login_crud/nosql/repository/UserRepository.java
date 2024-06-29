package com.development.login_crud.nosql.repository;

import com.development.login_crud.nosql.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
