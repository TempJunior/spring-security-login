package io.tempjunior.github.spring_security.infra.config;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {
    /***
     Essa classe serve para auditoria de usaurio
     Devolve um login do usuario logado a partir do contexto do Spring security
     ***/

    private UsuarioService service;

    public SecurityService(UsuarioService service){
        this.service = service;
    }

    public Usuario gettingLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return service.obterPorLogin(login);
    }
}
