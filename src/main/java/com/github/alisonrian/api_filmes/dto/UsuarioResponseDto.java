package com.github.alisonrian.api_filmes.dto;

import com.github.alisonrian.api_filmes.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.management.relation.Role;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto extends RepresentationModel<UsuarioResponseDto>{
    private Long id;
    private String nome;
    private List<FilmeRequestDto> favoritos;
    private Roles role;
}
