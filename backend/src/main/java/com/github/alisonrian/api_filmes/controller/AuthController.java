package com.github.alisonrian.api_filmes.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.alisonrian.api_filmes.domain.ListaNegra;
import com.github.alisonrian.api_filmes.dto.AuthLoginRequest;
import com.github.alisonrian.api_filmes.repository.ListaNegraRepository;
import com.github.alisonrian.api_filmes.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final ListaNegraRepository listaNegraRepository;
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody AuthLoginRequest loginRequest) {
        return service.login(loginRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            ListaNegra listaNegra = new ListaNegra(token);
            listaNegraRepository.save(listaNegra);
        }
        return ResponseEntity.ok().body("Logout realizado com sucesso!");
    }
    @GetMapping("/{token}")
    public ResponseEntity<?> getRole(@PathVariable String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        String userRole = decodedJWT.getClaim("role").asString();
        return ResponseEntity.ok(userRole);
    }

}
