package io.tempjunior.github.spring_security.web.controller;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import io.tempjunior.github.spring_security.infra.config.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
public class LivroController {

    private SecurityService service;

    public LivroController(SecurityService service){
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')") // Regra de acesso da ROLE / Ou pode colocar na propria classe
    public ResponseEntity<String> helloWord(){
        service.gettingLoggedUser();
        return ResponseEntity.ok().body("Olá MUDO!");
    }
}
