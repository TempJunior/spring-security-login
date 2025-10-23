package io.tempjunior.github.spring_security.infra.config;

import io.tempjunior.github.spring_security.domain.model.CustomUserDetailsService;
import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Essa anotação serve para poder configurar as roles dentro dos proprios endpoint
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config -> config.loginPage("/login").permitAll())// Customizer.withDefaults() é como se devolvesse o formulario padrão que o Spring entrega
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {//Precisa estar autenticado para qualquer requisição
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/", "/home", "/css/**", "/images/**", "/js/**", "/login").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/users/**").permitAll(); // Qualquer pessoa consegue criar um usuario
                    // HttpMethod.POST é uma Authorite, ou seja, o metodo POST da rota Autores, só pode ser feito por ADMIN
                    auth.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN"); // Na rota de autores somente os usuarios com a ROLE de ADMIN podem acessar
                    auth.anyRequest().authenticated(); // Fica por ultimo sempre
                })
                .oauth2Login(oauth2 -> oauth2.successHandler(successHandler)
                        .loginPage("/login")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Ignora o prefix de ROLE_
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    public UserDetailsService userDetailsService(UsuarioService service){

//        UserDetails user1 = User.builder()
//                .username("joselito")
//                .password(encoder.encode("joselito157"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("geovanna")
//                .password(encoder.encode("joselito157"))
//                .roles("ADMIN")
//                .build();

        return  new CustomUserDetailsService(service); // Devolve uma instancia de userDetails customizada
    }
}
