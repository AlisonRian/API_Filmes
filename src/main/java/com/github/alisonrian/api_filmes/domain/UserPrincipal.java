package com.github.alisonrian.api_filmes.domain;

import com.github.alisonrian.api_filmes.enums.Roles;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;


@Getter
public class UserPrincipal {
    private String username;
    private Collection<GrantedAuthority> authorities;
    private UserPrincipal(Usuario user){
        this.username = user.getNome();
            if (user.getRole()== Roles.ROLE_ADMIN){
                this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }else{
                this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            }

    }

    public static UserPrincipal create(Usuario user) {
        return new UserPrincipal(user);
    }

}
