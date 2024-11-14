package com.github.alisonrian.api_filmes.dto;

import java.util.List;

public class UsuarioRequestDto {
    private String nome;
    private String senha;
    private List<FilmeRequestDto> favoritos;
}
