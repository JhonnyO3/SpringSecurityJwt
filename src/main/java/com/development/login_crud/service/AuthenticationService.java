package com.development.login_crud.service;

import com.development.login_crud.dto.AuthenticateRequest;
import com.development.login_crud.dto.AuthenticationResponse;
import com.development.login_crud.dto.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse saveUser(RegisterRequest userRequest);

    AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest);

    AuthenticationResponse register(RegisterRequest request);
}
