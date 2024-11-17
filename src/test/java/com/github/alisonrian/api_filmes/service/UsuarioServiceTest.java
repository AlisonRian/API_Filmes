package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    List<Filme> favoritos = new ArrayList<>();
    private final Usuario usuarioValido = new Usuario("Usuario","Usuario@1322",favoritos);
    private final Usuario usuarioInvalido = new Usuario("", "", favoritos);

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Test
    public void criarUsuarioValido(){
        when(usuarioRepository.save(usuarioValido)).thenReturn(usuarioValido);
        assertThat(usuarioService.create(usuarioValido)).isEqualTo(usuarioValido);
    }
    @Test
    public void criarUsuarioInvalido(){
        when(usuarioRepository.save(usuarioInvalido)).thenThrow(RuntimeException.class);
        assertThatThrownBy(()-> usuarioService.create(usuarioInvalido)).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void BuscarUsuarioPorId(){
        usuarioValido.setId(1L);
        when(usuarioRepository.findById(usuarioValido.getId())).thenReturn(Optional.of(usuarioValido));
        assertThat(usuarioService.findById(usuarioValido.getId())).isEqualTo(usuarioValido);
        assertThat(usuarioService.findById(usuarioValido.getId())).isNotNull();
    }
    @Test
    public void BuscarUsuarioPorIdInvalido(){
        usuarioInvalido.setId(1L);
        when(usuarioRepository.findById(10L)).thenThrow(EntityNotFoundException.class);
        assertThatThrownBy(()-> usuarioService.findById(10L)).isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    public void DeletarUsuarioPorId(){
        usuarioValido.setId(1L);
        assertThatCode(()->usuarioService.delete(usuarioValido.getId())).doesNotThrowAnyException();
    }
    @Test
    public void DeletarUsuarioPorIdInvalido(){
        usuarioInvalido.setId(1L);
        doThrow(new RuntimeException()).when(usuarioRepository).deleteById(10L);
        assertThatThrownBy(()-> usuarioService.delete(usuarioInvalido.getId())).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void BuscarTodosUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioValido);
        usuarios.add(usuarioInvalido);
        Page<Usuario> paginaUsuarios = new PageImpl<>(usuarios);
        when(usuarioRepository.findAll(Pageable.unpaged())).thenReturn(paginaUsuarios);
        assertEquals(2, usuarioService.findAll(Pageable.unpaged()).getSize());
    }
}