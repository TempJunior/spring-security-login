package io.tempjunior.github.spring_security.infra.config;

import io.tempjunior.github.spring_security.domain.model.Usuario;
import io.tempjunior.github.spring_security.domain.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static String SENHA_PADRAO = "mudar@123";

    private final UsuarioService usuarioService;

    public LoginSocialSuccessHandler(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = authenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        Usuario usuario = usuarioService.obterPorEmail(email);

        if (usuario == null){
            cadastrarNaBaseComLoginSocial(email);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

    protected void cadastrarNaBaseComLoginSocial(String email){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(criaLogin(email));
        usuario.setPassword(SENHA_PADRAO);
        usuario.setRoles(List.of("USER"));
        usuarioService.create(usuario);
        logger.info("USUARIO CADASTRADO NA BASE - LOGIN: " + usuario.getLogin());
    }

    protected String criaLogin(String email){
        return email.substring(0, email.indexOf("@"));
    }
}
