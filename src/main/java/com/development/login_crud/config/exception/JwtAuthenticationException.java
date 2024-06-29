package com.development.login_crud.config.exception;


import io.jsonwebtoken.JwtException;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(String.format("A error occurred decoding the token: %s ", message));
    }

}
