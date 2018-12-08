package com.pfernand.pfonboard.pfonboard.port.database.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@RequiredArgsConstructor
public class User implements Serializable {
    @Builder.Default
    private final String firstName = null;
    @Builder.Default
    private final String lastName = null;
    private final String email;
    @Builder.Default
    private final String phoneNumber = null;
}
