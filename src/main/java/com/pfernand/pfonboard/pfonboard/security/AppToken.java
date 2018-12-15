package com.pfernand.pfonboard.pfonboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;

@Named
@RequiredArgsConstructor
public class AppToken {

    private static final String SUBJECT = "admin";
    private static final String CLAIM_PROPERTY = "authorities";
    private static final String CLAIM_VALUE = "ROLE_ADMIN";
    private static final int EXPIRATION_TIMES = 10000;

    private final JwtConfig jwtConfig;

    public String generateAppToken() {
        final Long now = System.currentTimeMillis();
        final Long expiration = now + (long)jwtConfig.getExpiration() * (long)EXPIRATION_TIMES;
        return Jwts.builder()
                .setSubject(SUBJECT)
                .claim(CLAIM_PROPERTY, Collections.singletonList(CLAIM_VALUE))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes(Charset.forName(jwtConfig.getCharset())))
                .compact();
    }

}
