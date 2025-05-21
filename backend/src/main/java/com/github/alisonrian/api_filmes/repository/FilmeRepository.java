package com.github.alisonrian.api_filmes.repository;

import com.github.alisonrian.api_filmes.domain.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    Page<Filme> findByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT f FROM Filme f WHERE " +
            "(COALESCE(:nome, '') = '' OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:anoLancamento IS NULL OR f.anoLancamento = :anoLancamento) AND " +
            "(:genero IS NULL OR f.genero = :genero)")
    Page<Filme> filtrarFilmes(@Param("nome") String nome,
                              @Param("anoLancamento") Integer anoLancamento,
                              @Param("genero") String genero,
                              Pageable pageable);
}
