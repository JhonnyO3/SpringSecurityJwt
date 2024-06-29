package com.development.login_crud.service;

import com.development.login_crud.config.exception.UserAlreadyExistsException;
import com.development.login_crud.dto.AuthenticateRequest;
import com.development.login_crud.dto.AuthenticationResponse;
import com.development.login_crud.dto.RegisterRequest;
import com.development.login_crud.nosql.entity.Token;
import com.development.login_crud.nosql.entity.TokenType;
import com.development.login_crud.nosql.entity.User;
import com.development.login_crud.nosql.repository.TokenRepository;
import com.development.login_crud.nosql.repository.UserRepository;
import com.development.login_crud.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse saveUser(RegisterRequest userRequest) {

        User user = User.builder()
                .email(userRequest.getUserEmail())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getUserPassword()))
                .build();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        User save = userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        var refreshToken = jwtUtil.generateRefreshToken(user);

        saveUserToken(save, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.getUserEmail(),
                authenticateRequest.getUserPassword()));


        var user = userRepository.findByEmail(authenticateRequest.getUserEmail())
                .orElseThrow();

        var jwtToken = jwtUtil.generateToken(user);
        var refreshToken = jwtUtil.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .dateTimeCreation(LocalTime.now())
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_IdAndRevokedIsFalseOrExpiredIsFalse(user.getId());
        if (!validUserTokens.isEmpty()) {
            List<Token> updatedTokens = validUserTokens.stream()
                    .peek(token -> {
                        token.setExpired(true);
                        token.setRevoked(true);
                    })
                    .toList();
            tokenRepository.saveAll(updatedTokens);
        }

    }
}
