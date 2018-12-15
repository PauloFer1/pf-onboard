package com.pfernand.pfonboard.pfonboard.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {

    private static final long serialVersionUID = -686567476484L;

    private final String uuid;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Instant createdAt;
}
