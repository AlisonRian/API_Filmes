package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.dto.UsuarioRequestDto;
import com.github.alisonrian.api_filmes.dto.UsuarioResponseDto;
import com.github.alisonrian.api_filmes.service.UsuarioService;
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


@CrossOrigin(origins = "*")
@RequestMapping("/usuarios/")
@RestController
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ModelMapper mapper;

    @GetMapping
    @Operation(description = "Listar todos os usuários cadastrados.")
    public Page<UsuarioResponseDto> listAll(Pageable pageable){
        Page<Usuario> usuariosPage = usuarioService.findAll(pageable);
        return usuariosPage.map(usuario ->{
            UsuarioResponseDto usuarioDto = convertToDto(usuario);
            usuarioDto.add(linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel());
            return usuarioDto;
        });
    }
    @GetMapping("{id}")
    @Operation(description = "Encontrar filme específico por id.")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id){
        UsuarioResponseDto usuarioResponseDto = convertToDto(usuarioService.findById(id));
        usuarioResponseDto.add(linkTo(methodOn(UsuarioController.class).listAll(Pageable.unpaged())).withRel("Lista de Usuários:"));
        return ResponseEntity.ok(usuarioResponseDto);
    }
    @PostMapping
    @Operation(description = "Cadastrar novo usuário.")
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
    @Operation(description = "Atualizar usuário existente.")
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
    @Operation(description = "Deletar usuário por id.")
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
