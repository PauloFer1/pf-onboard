package com.pfernand.pfonboard.pfonboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.Collections;
import java.util.Date;

@Named
@RequiredArgsConstructor
public class AppToken {

    private final JwtConfig jwtConfig;

    public String generateAppToken() {
        Long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject("admin")
                .claim("authorities", Collections.singletonList("ROLE_ADMIN"))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

}
