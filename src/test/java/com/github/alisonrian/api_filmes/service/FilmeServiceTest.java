package com.github.alisonrian.api_filmes.service;

import com.github.alisonrian.api_filmes.domain.Filme;
import com.github.alisonrian.api_filmes.repository.FilmeRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FilmeServiceTest {
    Filme filmeValido = new Filme("Gladiador","Ação","14 anos","Maximo, um general das legiões",2000, "" );
    Filme filmeInvalido = new Filme("","","","",3000,"");
    @InjectMocks
    private FilmeService filmeService;
    @Mock
    private FilmeRepository filmeRepository;

    @Test
    public void criarFilmeValido(){
        when(filmeRepository.save(filmeValido)).thenReturn(filmeValido);
        assertThat(filmeService.create(filmeValido)).isEqualTo(filmeValido);
    }
    @Test
    public void criarFilmeInvalido(){
        when(filmeRepository.save(filmeInvalido)).thenThrow(RuntimeException.class);
        assertThatThrownBy(()->filmeService.create(filmeInvalido)).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void listarFilmes(){
        List<Filme> filmes = new ArrayList<>();
        filmes.add(filmeValido);
        filmes.add(filmeInvalido);
        Page<Filme> filmesPage = new PageImpl<>(filmes);
        when(filmeRepository.findAll(Pageable.unpaged())).thenReturn(filmesPage);
        assertEquals(2,filmeService.findAll(Pageable.unpaged()).getSize());
    }
    @Test
    public void buscarFilmeValidoPorId(){
        filmeValido.setId(1L);
        when(filmeRepository.findById(1L)).thenReturn(Optional.of(filmeValido));
        assertThat(filmeService.findById(1L)).isEqualTo(filmeValido);
    }
    @Test
    public void buscarFilmeInvalidoPorId(){
        filmeInvalido.setId(2L);
        when(filmeRepository.findById(10L)).thenThrow(EntityNotFoundException.class);
        assertThatThrownBy(()->filmeService.findById(10L)).isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    public void deletarFilmeValido(){
        filmeValido.setId(1L);
        assertThatCode(()->filmeService.delete(1L)).doesNotThrowAnyException();
    }
    @Test
    public void deletarFilmeNaoEncontrado(){
        filmeInvalido.setId(2L);
        doThrow(RuntimeException.class).when(filmeRepository).deleteById(10L);
        assertThatThrownBy(()->filmeService.delete(10L)).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void editarFilme(){
        filmeInvalido.setId(2L);
        filmeValido.setId(1L);
        when(filmeRepository.saveAndFlush(filmeInvalido)).thenReturn(filmeInvalido);
        Filme resultado = filmeService.update(filmeInvalido, 1L);
        assertThat(resultado).isEqualTo(filmeInvalido);
    }
}