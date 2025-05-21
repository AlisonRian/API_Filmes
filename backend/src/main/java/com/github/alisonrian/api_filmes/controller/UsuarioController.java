package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.domain.UserPrincipal;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.dto.FilmeResponseDto;
import com.github.alisonrian.api_filmes.dto.UsuarioRequestDto;
import com.github.alisonrian.api_filmes.dto.UsuarioResponseDto;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import com.github.alisonrian.api_filmes.service.FilmeService;
import com.github.alisonrian.api_filmes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/usuarios/")
@RestController
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ModelMapper mapper;
    private final UsuarioRepository repository;
    private final FilmeService filmeService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(description = "Listar todos os usuários cadastrados.")
    public ResponseEntity<Page<UsuarioResponseDto>> listAll(Pageable pageable){
        Page<Usuario> usuarioPage = usuarioService.findAll(pageable);
        Page<UsuarioResponseDto> response = usuarioPage.map(this::convertToDto);
        return ResponseEntity.ok(response);
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
        Usuario up = convertToEntity(usuarioRequestDto);
        up.setId(id);
        Usuario update = usuarioService.update(up, id);
        return ResponseEntity.ok(convertToDto(update));
    }
    @PutMapping("/favoritos/{id}")
    @Operation(description = "Adicionar filme aos favoritos.")
    @ResponseStatus(HttpStatus.OK)
    public void addFavorite(@PathVariable("id") Long id){
        Usuario u = usuarioService.findById(userLogged());
        Filme f = filmeService.findById(id);
        if(usuarioRepository.findFilmeFavorito(u.getId(), f.getId()).isEmpty()){
            u.getFavoritos().add(f);
            usuarioService.update(u,u.getId());
        }
    }
    @DeleteMapping("/favoritos/{id}")
    @Operation(description = "Remover filme dos favoritos.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@PathVariable("id") Long id){
        Usuario u = usuarioService.findById(userLogged());
        Filme f = filmeService.findById(id);
        usuarioRepository.removeFilmeFavorito(u.getId(),f.getId());
    }
    @GetMapping("/favoritos")
    @Operation(description = "Retorna todos os filmes favoritados do usuário.")
    public ResponseEntity<Page<Filme>> listAllFavoritos(Pageable pageable){
        Usuario u =  usuarioService.findById(userLogged());
        List<Filme> filmes = u.getFavoritos();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), filmes.size());
        final Page<Filme> page = new PageImpl<>(filmes.subList(start, end), pageable, filmes.size());
        return ResponseEntity.ok(page);
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
     public Long userLogged(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
         Optional<Usuario> u = repository.findByNome(userPrincipal.getUsername());
         return u.get().getId();
     }


}
