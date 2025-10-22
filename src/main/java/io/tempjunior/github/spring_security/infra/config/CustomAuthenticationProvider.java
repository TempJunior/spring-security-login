package io.tempjunior.github.spring_security.infra.config;

import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    public CustomAuthenticationProvider(UsuarioService usuarioService, PasswordEncoder encoder){
        this.usuarioService = usuarioService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();
        var usuarioEncontrado = usuarioService.obterPorLogin(login);

        if (usuarioEncontrado == null){
            throw new UsernameNotFoundException("Usuario ou senha encorretas");
        }

        String senhaCriptografada = usuarioEncontrado.getPassword();
        boolean senhaBate = encoder.matches(senhaCriptografada, senhaCriptografada); //Sempre nessa sequencia de parametro

        if (senhaBate){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw new UsernameNotFoundException("Usuario ou senha incorretas!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
