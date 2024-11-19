package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.config.FileStorageProperties;
import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.dto.FilmeRequestDto;
import com.github.alisonrian.api_filmes.dto.FilmeResponseDto;
import com.github.alisonrian.api_filmes.service.FilmeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@AllArgsConstructor
@RestController
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeService filmeService;
    private final ModelMapper mapper;
    private final FileStorageController fileStorageController;
    @PostMapping
    public ResponseEntity<FilmeResponseDto> create(@Valid @RequestBody FilmeRequestDto filmeRequestDto) {
        Filme created = filmeService.create(convertToEntity(filmeRequestDto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(created));
    }

    @GetMapping
    public Page<FilmeResponseDto> listAll(Pageable pageable){
        Page<Filme> filmePage = filmeService.findAll(pageable);
        return filmePage.map(this::convertToDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        filmeService.delete(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<FilmeResponseDto> update(@RequestBody FilmeRequestDto filmeRequestDto, @PathVariable("id") Long id){
        try{
            Filme update = filmeService.findById(id);
        }catch(Exception e){
            return this.create(filmeRequestDto);
        }
        Filme update = filmeService.update(convertToEntity(filmeRequestDto), id);
        return ResponseEntity.ok(convertToDto(update));
    }

    public FilmeResponseDto convertToDto(Filme filme){
        return mapper.map(filme, FilmeResponseDto.class);
    }
    public Filme convertToEntity(FilmeRequestDto requestDto){
        return mapper.map(requestDto, Filme.class);
    }
}
