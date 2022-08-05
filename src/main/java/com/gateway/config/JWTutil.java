package com.gateway.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTutil implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final long jwtTokenValidity = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public String doGenerateToken(Map<String, Object> Claims, String subject) {
        return Jwts.builder().setClaims(null).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Claims getAllClaimsfromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimresolver) {
        final Claims claims = getAllClaimsfromToken(token);
        return claimresolver.apply(claims);

    }

    public String getUserNamefromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationfromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public Boolean isExpired(String token) {
        Date expiryDate = getClaimsFromToken(token, Claims::getExpiration);
        return expiryDate.before(new Date());

    }

    public Boolean isValidToken(String token, UserDetails user) {
        Boolean correctuser = user.getUsername().equals(getUserNamefromToken(token));
        return (correctuser && !isExpired(token));

    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

}
