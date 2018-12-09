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

    private static final String SUBJECT = "admin";
    private static final String CLAIM_PROPERTY = "authorities";
    private static final String CLAIM_VALUE = "ROLE_ADMIN";
    private static final int EXPIRATION_TIMES = 1000;

    private final JwtConfig jwtConfig;

    public String generateAppToken() {
        Long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(SUBJECT)
                .claim(CLAIM_PROPERTY, Collections.singletonList(CLAIM_VALUE))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * EXPIRATION_TIMES))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

}
