package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericCrud<Usuario, Long, UsuarioRepository> {
    private UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.repository = repository;
    }
    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return repository.findByDeletedAtIsNull(pageable);
    }
}
