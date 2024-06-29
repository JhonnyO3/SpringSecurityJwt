package com.development.login_crud.config.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s Already exists", email));
    }
}
