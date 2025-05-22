package com.github.alisonrian.api_filmes.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alisonrian.api_filmes.domain.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

public class SecurityUtils {
    private static final String ISSUR = "cine-api";
    private static final String TOKEN_KEY = "+opjMf@-4=+&l]>?(WZ?v?/v^fQw2i";
    private static final long EXPIRATION =  60 * 60 * 1000;
    public static String encode(Usuario user) {
        var expiration = new Date(System.currentTimeMillis() + EXPIRATION);
        TokenResponse tokenResponse = TokenResponse.create(createTokenJWT(user, expiration), expiration);
        try {
            return new ObjectMapper().writeValueAsString(tokenResponse);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error creating token response");
        }
    }

    public static String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_KEY.getBytes());

        try{
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(ISSUR)
                    .acceptExpiresAt(5)
                    .build();
            return jwtVerifier.verify(token).getSubject();
        }catch (JWTVerificationException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidPassword(String password, String savedPassword) {
        if(password == null || password.trim().isBlank()) return false;
        return new BCryptPasswordEncoder().matches(password, savedPassword);
    }

    private static String createTokenJWT(Usuario user, Date expiration) {
        String role = user.getRole().toString();
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_KEY.getBytes());
            return JWT.create()
                    .withSubject(user.getNome())
                    .withIssuer(ISSUR)
                    .withExpiresAt(expiration)
                    .withClaim("role", role)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new IllegalArgumentException("Error creating token");
        }
    }
}
