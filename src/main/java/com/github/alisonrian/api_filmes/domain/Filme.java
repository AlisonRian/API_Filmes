package com.github.alisonrian.api_filmes.domain;

import com.github.alisonrian.api_filmes.validation.ano.AnoValido;
import jakarta.persistence.Column;
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
@SQLDelete(sql="UPDATE filme SET deleted_at = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")
@Entity
public class Filme extends EntidadeAbstrata {
    @NotBlank(message = "O nome não pode ficar em branco.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    private String nome;

    @NotBlank(message = "O genero não pode ficar em branco.")
    @Size(min = 3, max = 100, message = "O genero deve ter entre 3 a 100 caracteres")
    private String genero;

    @NotBlank(message = "O nome não pode ficar em branco.")
    private String classificacao;

    @NotBlank(message = "A sinopse não pode ficar em branco.")
    @Size(min = 10, message = "A sinopse deve ter no mínimo 10 caracteres.")
    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @AnoValido
    private int anoLancamento;

    @Column(length = 500)
    private String imagemUri;
}
