package com.pfernand.pfonboard.pfonboard.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Data
@Named
public class JwtConfig {

    private final int expiration;

    private final String secret;

    @Inject
    public JwtConfig(@Value("${security.jwt.expiration:#{24*60*60}}") final int expiration,
                     @Value("${security.jwt.secret:JwtSecretKey}") final String secret) {
        this.expiration = expiration;
        this.secret = secret;
    }
}
