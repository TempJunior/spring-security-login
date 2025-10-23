package io.tempjunior.github.spring_security.web.controller;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import io.tempjunior.github.spring_security.web.dto.request.UsuarioDTO;
import io.tempjunior.github.spring_security.web.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UsuarioDTO userDTO){
        var user = new Usuario(userDTO);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(user));
    }
}
