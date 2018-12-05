package com.pfernand.pfonboard.pfonboard.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Instant createdAt;
}
