package com.github.alisonrian.api_filmes.domain;

import com.github.alisonrian.api_filmes.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql="UPDATE usuario SET deleted_at = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")
@Entity
@Builder
public class Usuario extends EntidadeAbstrata{
    @NotBlank(message = "O nome não pode ficar em branco.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 a 50 caracteres")
    @Column(unique = true, nullable = false)
    private String nome;


    private String senha;
    //          Ter entre 8 e 20 caracteres.
    //        Incluir pelo menos uma letra maiúscula.
    //        Incluir pelo menos uma letra minúscula.
    //        Incluir pelo menos um dígito.
    //        Incluir pelo menos um caractere especial (por exemplo, @, #, !, etc.).

    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany
    @JoinTable(name="usuario_filme",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="filme_id")
    )
    private List<Filme> favoritos = new ArrayList<>();

}
