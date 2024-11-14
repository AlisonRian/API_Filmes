package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.dto.UsuarioRequestDto;
import com.github.alisonrian.api_filmes.dto.UsuarioResponseDto;
import com.github.alisonrian.api_filmes.service.FilmeService;
import com.github.alisonrian.api_filmes.service.UsuarioService;
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

@RequestMapping("/usuarios")
@RestController
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ModelMapper mapper;

    @GetMapping
    public Page<UsuarioResponseDto> listAll(Pageable pageable){
        Page<Usuario> usuarios = usuarioService.findAll(pageable);
        return usuarios.map(this::convertToDto);
    }
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto){
        Usuario usuario = usuarioService.create(convertToEntity(usuarioRequestDto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(usuario));
    }
    @PutMapping("{id}")
    public ResponseEntity<UsuarioResponseDto> update(@PathVariable("id") Long id, @RequestBody UsuarioRequestDto usuarioRequestDto){
        try{
            Usuario updated = usuarioService.findById(id);
        }catch(Exception e){
            return this.create(usuarioRequestDto);
        }
        Usuario updated = usuarioService.update(convertToEntity(usuarioRequestDto), id);
        return ResponseEntity.ok(convertToDto(updated));
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        usuarioService.delete(id);
    }

    public UsuarioResponseDto convertToDto(Usuario usuario){
        return mapper.map(usuario, UsuarioResponseDto.class);
    }
    public Usuario convertToEntity(UsuarioRequestDto usuarioRequestDto){
       return mapper.map(usuarioRequestDto, Usuario.class);
    }

}
