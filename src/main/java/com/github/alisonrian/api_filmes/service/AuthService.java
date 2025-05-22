package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.config.SecurityUtils;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.dto.AuthLoginRequest;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public String login(AuthLoginRequest loginRequest) {
        if(loginRequest == null)
            throw new IllegalArgumentException("login request must not be null");

        Optional<Usuario> optUser = usuarioRepository.findByNomeIgnoreCase(loginRequest.username());
        if(optUser.isEmpty())
            throw new RuntimeException("User not exists");

        var passIsValid = SecurityUtils.isValidPassword(loginRequest.password(), optUser.get().getSenha());

        if(passIsValid){
            return SecurityUtils.encode(optUser.get());
        }else {
            throw new RuntimeException("User not authorized");
        }
    }

}
