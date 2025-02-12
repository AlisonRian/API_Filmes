package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.config.FileStorageProperties;
import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.dto.FilmeRequestDto;
import com.github.alisonrian.api_filmes.dto.FilmeResponseDto;
import com.github.alisonrian.api_filmes.dto.UsuarioResponseDto;
import com.github.alisonrian.api_filmes.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
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

@CrossOrigin(origins = "*")
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
    public Page<FilmeResponseDto> listAll(Pageable pageable){
        Page<Filme> filmePage = filmeService.findAll(pageable);
        return filmePage.map(filme ->{
            FilmeResponseDto filmeDto = convertToDto(filme);
            filmeDto.add(linkTo(methodOn(FilmeController.class).findById(filme.getId())).withSelfRel());
            return filmeDto;
        });
    }
    @GetMapping("filtrar")
    @Operation(description = "Filtrar filmes pelo ano de lançamento.")
    public Page<FilmeResponseDto> listByNome(@RequestParam (required = false) String nome,
                                             @RequestParam (required = false) String genero,
                                             @RequestParam (required = false) Integer anoLancamento,
                                             Pageable pageable){
        Page<Filme> filmePage = filmeService.filtrarFilmes(nome,genero,anoLancamento,pageable);
        return filmePage.map(filme ->{
            FilmeResponseDto filmeDto = convertToDto(filme);
            filmeDto.add(linkTo(methodOn(FilmeController.class).findById(filme.getId())).withSelfRel());
            return filmeDto;
        });
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
        try{
            Filme up = convertToEntity(filmeRequestDto);
            up.setId(id);
            Filme update = filmeService.update(up, id);
            return ResponseEntity.ok(convertToDto(update));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    public FilmeResponseDto convertToDto(Filme filme){
        return mapper.map(filme, FilmeResponseDto.class);
    }
    public Filme convertToEntity(FilmeRequestDto requestDto){
        return mapper.map(requestDto, Filme.class);
    }
}
