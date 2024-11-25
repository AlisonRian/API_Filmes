package com.github.alisonrian.api_filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto extends RepresentationModel<UsuarioResponseDto>{
    private String nome;
    private List<FilmeRequestDto> favoritos;
}
