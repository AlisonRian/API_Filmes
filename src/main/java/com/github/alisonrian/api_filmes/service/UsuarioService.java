package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;


@Service
public class UsuarioService extends GenericCrud<Usuario, Long, UsuarioRepository> {
    private UsuarioRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private View error;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return repository.findByDeletedAtIsNull(pageable);
    }
    public Usuario create(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
}
