package com.github.alisonrian.api_filmes.repository;

import com.github.alisonrian.api_filmes.domain.ListaNegra;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ListaNegraRepository extends JpaRepository<ListaNegra, Long> {
    boolean existsByToken(String token);
}
