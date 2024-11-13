package com.github.alisonrian.api_filmes.domain;

import com.github.alisonrian.api_filmes.validation.ano.AnoValido;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE filme SET deletedAT = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")
@Entity
public class Filme extends EntidadeAbstrata {
    @NotBlank(message = "O nome não pode ficar em branco.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 a 50 caracteres")
    private String nome;

    @NotBlank(message = "O genero não pode ficar em branco.")
    @Size(min = 3, max = 50, message = "O genero deve ter entre 3 a 50 caracteres")
    private String genero;

    @NotBlank(message = "O nome não pode ficar em branco.")
    private String classificacao;

    @AnoValido
    private int anoLancamento;

    private String imagemUri;
}
