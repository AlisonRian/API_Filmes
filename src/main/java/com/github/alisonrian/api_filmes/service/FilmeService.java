package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.repository.FilmeRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmeService extends GenericCrud<Filme, Long, FilmeRepository> {
    FilmeService(FilmeRepository repository) {
        super(repository);
    }
}
