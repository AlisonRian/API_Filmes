package com.github.alisonrian.api_filmes.domain;

import jakarta.persistence.Entity;
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
    private String nome;
    private String genero;
    private String classificacao;
    private String imagemUri;
    private int anoLancamento;
}
