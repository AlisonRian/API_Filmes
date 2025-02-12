package com.github.alisonrian.api_filmes.repository;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByDeletedAtIsNull(Pageable pageable);
}
