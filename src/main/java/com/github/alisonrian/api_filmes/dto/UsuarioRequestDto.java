package com.github.alisonrian.api_filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {
    private String nome;
    private String senha;
    private List<FilmeRequestDto> favoritos;
}
