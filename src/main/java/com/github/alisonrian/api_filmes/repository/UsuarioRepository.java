package com.github.alisonrian.api_filmes.repository;



import com.github.alisonrian.api_filmes.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByDeletedAtIsNull(Pageable pageable);
    Optional<Usuario> findByNomeIgnoreCase(String nome);
    Optional<Usuario> findByNome(String nome);
    @Query(value = "SELECT * FROM usuario_filme WHERE usuario_id = :id AND filme_id = :filme_id", nativeQuery = true)
    List<Optional<Object>> findFilmeFavorito(@Param("id") Long id, @Param("filme_id") Long filmeId);
    @Query(value = "DELETE FROM usuario_filme WHERE usuario_id = :id AND filme_id = :filme_id", nativeQuery = true)
    void removeFilmeFavorito(@Param("id") Long id, @Param("filme_id") Long filmeId);
}
