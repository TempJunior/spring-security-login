package io.tempjunior.github.spring_security.domain.model;

import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    public CustomUserDetailsService(UsuarioService service) {
        this.service = service;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario user = service.obterPorLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}
