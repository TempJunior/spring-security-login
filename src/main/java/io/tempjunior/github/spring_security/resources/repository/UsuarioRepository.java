package io.tempjunior.github.spring_security.resources.repository;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
    Usuario findByEmail(String email);
}
