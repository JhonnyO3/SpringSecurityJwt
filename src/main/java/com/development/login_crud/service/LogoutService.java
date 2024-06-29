package com.development.login_crud.service;

import com.development.login_crud.nosql.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LogoutService implements LogoutHandler {
    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (Objects.nonNull(authHeader) || authHeader.contains("Bearer ")) {
            jwt = authHeader.substring(7);
            var storedToken = tokenRepository.findByToken(jwt)
                    .orElse(null);

            if (Objects.nonNull(storedToken)) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
                SecurityContextHolder.clearContext();
            }

        }




    }
}
