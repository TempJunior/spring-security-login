package io.tempjunior.github.spring_security.domain.service;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import io.tempjunior.github.spring_security.resources.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public void create(Usuario usuario){
        var senha = usuario.getPassword();
        usuario.setPassword(encoder.encode(senha));

        repository.save(usuario);
    }

    @Transactional
    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }
}
