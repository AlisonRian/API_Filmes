package com.github.alisonrian.api_filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmeRequestDto {
    private String nome;
    private String genero;
    private String classificacao;
    private String sinopse;
    private int anoLancamento;
    private String imagemUri;
}
