package com.github.alisonrian.api_filmes.config;

import com.github.alisonrian.api_filmes.domain.UserPrincipal;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.repository.ListaNegraRepository;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private final UsuarioRepository userRepository;
    private final ListaNegraRepository listaNegraRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getBearerAuthentication(request);

        if(token.isPresent()){

            if(listaNegraRepository.existsByToken(token.get())){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado.");
                return;
            }

            String username = SecurityUtils.verifyToken(token.get());
            Optional<Usuario> user = userRepository.findByNomeIgnoreCase(username);

            if(user.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("O nome não existe ou senha inválida!");
                return;
            }

            UserPrincipal principal = UserPrincipal.create(user.get());
            setAuthentication(principal);
        }
        filterChain.doFilter(request, response);
    }


    private void setAuthentication(UserPrincipal p) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(p, null, p.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private Optional<String> getBearerAuthentication(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        return header != null && header.startsWith(BEARER) ?
                Optional.of(header.replace(BEARER, "")) : Optional.empty();
    }
}
