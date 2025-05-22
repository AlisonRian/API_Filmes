package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.repository.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeService extends GenericCrud<Filme, Long, FilmeRepository> {
    private final FilmeRepository filmeRepository;

    FilmeService(FilmeRepository repository, FilmeRepository filmeRepository) {
        super(repository);
        this.filmeRepository = filmeRepository;
    }

    @Override
    public Page<Filme> findAll(Pageable pageable) {
        return filmeRepository.findByDeletedAtIsNull(pageable);
    }
    public Page<Filme> filtrarFilmes(String nome, String genero, Integer anoLancamento, Pageable pageable) {
        return filmeRepository.filtrarFilmes(nome,anoLancamento,genero, pageable);
    }
}
