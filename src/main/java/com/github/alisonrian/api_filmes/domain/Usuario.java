package com.github.alisonrian.api_filmes.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql="UPDATE usuario SET deletedAt = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")
@Entity
public class Usuario extends EntidadeAbstrata{
    private String nome;
    private String senha;
    private String email;
}
