package com.github.alisonrian.api_filmes.config;

import java.util.Date;

public record TokenResponse(String token, Date expiration) {

    public static TokenResponse create(String tokenJWT, Date expiration) {
        if(tokenJWT == null)
            throw new IllegalArgumentException("tokenJWT must not be null");

        return new TokenResponse(tokenJWT, expiration);
    }
}
