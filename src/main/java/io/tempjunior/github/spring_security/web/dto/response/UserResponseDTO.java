package io.tempjunior.github.spring_security.web.dto.response;

import io.tempjunior.github.spring_security.domain.model.Usuario;

import java.util.List;

public record UserResponseDTO(String login,String email, List<String> roles) {
    public UserResponseDTO(Usuario user) {
        this(user.getLogin(), user.getEmail(), user.getRoles());
    }
}
