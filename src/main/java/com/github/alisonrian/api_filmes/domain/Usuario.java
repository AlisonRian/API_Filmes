package com.github.alisonrian.api_filmes.domain;

import com.github.alisonrian.api_filmes.validation.senha.SenhaValida;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "O nome não pode ficar em branco.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 a 50 caracteres")
    private String nome;
    @SenhaValida
    private String senha;
//          Ter entre 8 e 20 caracteres.
//        Incluir pelo menos uma letra maiúscula.
//        Incluir pelo menos uma letra minúscula.
//        Incluir pelo menos um dígito.
//        Incluir pelo menos um caractere especial (por exemplo, @, #, !, etc.).
}
