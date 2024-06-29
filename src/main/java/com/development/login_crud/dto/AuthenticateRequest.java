package com.development.login_crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticateRequest {

    @Email
    @NotEmpty(message = "Email não pode estar vazio!")
    private String userEmail;

    @NotEmpty(message = "Senha não pode estar vazio!")
    private String userPassword;



}
