package com.development.login_crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    @NotEmpty(message = "Nome de usuario não pode estar vazio!")
    private String username;

    @Email
    @NotEmpty(message = "Email não pode estar vazio!")
    private String userEmail;

    @NotEmpty(message = "Senha não pode estar vazio!")
    private String userPassword;

    @NotEmpty(message = "O usuario deve ter uma Role")
    private Role role;




}
