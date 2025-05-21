package com.github.alisonrian.api_filmes.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.alisonrian.api_filmes.domain.UserPrincipal;
import com.github.alisonrian.api_filmes.domain.Usuario;
import com.github.alisonrian.api_filmes.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

@Component
@RequiredArgsConstructor
public class CredentialsAuthenticationFilter extends OncePerRequestFilter {
    private static final String BASIC = "Basic ";
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var credentials = getBasicAuthentication(request);
        if(credentials.isPresent()){
            String[] listCredencials = decodeBase64(credentials.get()).split(":");

            var username = listCredencials[0];
            var password = listCredencials[1];

            Optional<Usuario> user = usuarioRepository.findByNomeIgnoreCase(username);

            if(user.isEmpty() || !isValidPassword(password, user.get().getSenha())){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Username does not exists or password is invalid");
                return;
            }
            setAuthentication(user.get());
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Usuario user) {
        Authentication authentication = createAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthentication(Usuario user) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private boolean isValidPassword(String password, String savedPassword) {
        if(password == null || password.trim().isBlank()) return false;
        return  passwordEncoder.matches(password, savedPassword);
    }

    private String decodeBase64(String base64){
        byte[] decodeBytes = Base64.getDecoder().decode(base64);
        return new String(decodeBytes);
    }

    private Optional<String> getBasicAuthentication(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        return header != null && header.startsWith(BASIC) ?
                Optional.of(header.replace(BASIC, "")) : Optional.empty();
    }
}
