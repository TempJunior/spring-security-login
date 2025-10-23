package io.tempjunior.github.spring_security.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Campo login obrigatorio")
        String login,
        @NotBlank(message = "Campo password obrigatorio")
        String password,
        @Email(message = "Email Invalido.")
        String email,
        List<String> roles) {
}
