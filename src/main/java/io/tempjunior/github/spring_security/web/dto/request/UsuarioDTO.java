package io.tempjunior.github.spring_security.web.dto.request;

import java.util.List;

public record UsuarioDTO(String login, String password, List<String> roles) {
}
