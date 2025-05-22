package com.github.alisonrian.api_filmes.dto;

import com.github.alisonrian.api_filmes.enums.Roles;
import com.github.alisonrian.api_filmes.validation.senha.SenhaValida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {
    private String nome;
    @SenhaValida
    private String senha;
    private List<FilmeRequestDto> favoritos;
    private Roles role;
}
