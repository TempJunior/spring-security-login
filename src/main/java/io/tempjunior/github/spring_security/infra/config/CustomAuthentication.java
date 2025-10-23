package io.tempjunior.github.spring_security.infra.config;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomAuthentication implements Authentication {
    /**
     * @author juniorTemp <br>
     * Classe que é responsavel por criar uma Autenticação customizavel
     * e devolver um autenticação quando ouver um login. <br>
     * Possui os metodos gerais para pegar as credenciais do usuario que está
     * autenticado na aplicação.
     * */

    private final Usuario usuario;

    public CustomAuthentication(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true; // Sempre true
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return usuario.getLogin();
    }
}
