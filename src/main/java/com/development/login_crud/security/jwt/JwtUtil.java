package com.development.login_crud.security.jwt;

import com.development.login_crud.nosql.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret}")
    private String JWT_SECRET;

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
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
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
                .setClaims(claims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
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
