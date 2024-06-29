package com.development.login_crud.nosql.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Document
@Builder
@Data
public class Token {

    @Id
    public String id;

    public String token;

    public TokenType tokenType;

    public boolean revoked;

    public boolean expired;

    public LocalTime dateTimeCreation;

    public LocalTime datetimeExpiration;

    public User user;
}
