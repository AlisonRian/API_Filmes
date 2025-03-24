package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.dto.FilmeRequestDto;
import com.github.alisonrian.api_filmes.dto.FilmeResponseDto;
import com.github.alisonrian.api_filmes.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/filmes/")
public class FilmeController {
    private final FilmeService filmeService;
    private final ModelMapper mapper;
    private final FileStorageController fileStorageController;
    @PostMapping
    @Operation(description = "Cadastrar um novo filme.")
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
    @Operation(description = "Listar todos os filmes cadastrados.")
    public ResponseEntity<Page<FilmeResponseDto>> listAll(Pageable pageable){
        Page<Filme> filmePage = filmeService.findAll(pageable);
        Page<FilmeResponseDto> response = filmePage.map(this::convertToDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("filtrar")
    @Operation(description = "Filtrar filmes pelo ano de lançamento.")
    public ResponseEntity<Page<FilmeResponseDto>> listByNome(@RequestParam (required = false) String nome,
                                             @RequestParam (required = false) String genero,
                                             @RequestParam (required = false) Integer anoLancamento,
                                             Pageable pageable){
        Page<Filme> filmePage = filmeService.filtrarFilmes(nome,genero,anoLancamento,pageable);
        if (filmePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(filmePage.map(this::convertToDto));
    }


    @GetMapping("{id}")
    @Operation(description = "Encontrar filme específico por id.")
    public ResponseEntity<FilmeResponseDto> findById(@PathVariable Long id) {
        FilmeResponseDto filmeResponseDto = convertToDto(filmeService.findById(id));
        filmeResponseDto.add(linkTo(methodOn(FilmeController.class).listAll(Pageable.unpaged())).withRel("Lista de filmes:"));
        return ResponseEntity.ok(filmeResponseDto);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Deletar filme por id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        filmeService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(description = "Atualizar filme existente.")
    public ResponseEntity<FilmeResponseDto> update(@RequestBody FilmeRequestDto filmeRequestDto, @PathVariable("id") Long id){
            Filme up = convertToEntity(filmeRequestDto);
            up.setId(id);
            if(up.getImagemUri().isEmpty()){
                Filme f = filmeService.findById(id);
                up.setImagemUri(f.getImagemUri());
            }
            Filme update = filmeService.update(up, id);
            return ResponseEntity.ok(convertToDto(update));
    }


    public FilmeResponseDto convertToDto(Filme filme){
        return mapper.map(filme, FilmeResponseDto.class);
    }
    public Filme convertToEntity(FilmeRequestDto requestDto){
        return mapper.map(requestDto, Filme.class);
    }
}
