package com.jb.online_quiz_app.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider 
{
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication)
    {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireData = new Date(currentDate.getTime() + jwtExpirationDate);
        
        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireData).signWith(key()).compact();
        return token;
    }

    private Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //jwt token by username - email
    public String getUsername(String token)
    {
        Claims claims= Jwts.parserBuilder()
                           .setSigningKey(key())
                           .build()
                           .parseClaimsJws(token)
                           .getBody();
        String username = claims.getSubject();
        return username;
    }

    //validate jwt token
    public boolean validateToken(String token)
    {
        Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parse(token);
        return true;
    }
}