package com.development.login_crud.security.jwt;

import com.development.login_crud.nosql.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private  String JWT_SECRET;

    @Value("${security.jwt.expiration}")
    private Long JWT_EXPIRATION_TIME;

    @Value("${security.jwt.refresh.expiration}")
    private Long JWT_REFRESH_EXPIRATION_TIME;


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
        return createToken(extraClaims, userDetails, JWT_EXPIRATION_TIME);
    }


    public String generateRefreshToken(
            User userDetails
    ) {
        return createToken(new HashMap<>(), userDetails, JWT_REFRESH_EXPIRATION_TIME);
    }

    private String createToken(Map<String, Object> claims, User userDetails, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, User userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }


}
