package io.tempjunior.github.spring_security.domain.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.tempjunior.github.spring_security.web.dto.request.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles")
    private List<String> roles;

    public Usuario(UsuarioDTO user){
        this.login = user.login();
        this.password = user.password();
        this.roles = user.roles();
    }
}
