package com.github.alisonrian.api_filmes.repository;

import com.github.alisonrian.api_filmes.domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
