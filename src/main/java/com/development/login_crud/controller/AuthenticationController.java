package com.development.login_crud.controller;

import com.development.login_crud.dto.AuthenticateRequest;
import com.development.login_crud.dto.AuthenticationResponse;
import com.development.login_crud.dto.RegisterRequest;
import com.development.login_crud.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody  @Valid RegisterRequest request) {

        return ResponseEntity.ok(authenticationService.saveUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }


}
