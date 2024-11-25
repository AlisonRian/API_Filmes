package com.github.alisonrian.api_filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmeResponseDto extends RepresentationModel<FilmeResponseDto>{
    private String nome;
    private String genero;
    private String classificacao;
    private String sinopse;
    private int anoLancamento;
    private String imagemUri;
}
