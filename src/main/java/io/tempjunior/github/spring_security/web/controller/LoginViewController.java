package io.tempjunior.github.spring_security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // RestController é para API - requisições REST trata com JSON e recebe JSON - Controller apenas para paginas WEB
public class LoginViewController {

    private LivroController livroController;

    public LoginViewController(LivroController livroController){
        this.livroController = livroController;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login"; // Retorna a pagina do HTML que tambem foi setada no WebConfiguration
    }

    @GetMapping("/")
    //@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')") // Regra de acesso da ROLE / Ou pode colocar na propria classe
    public String homePage(){
        return "home";
    }
}
