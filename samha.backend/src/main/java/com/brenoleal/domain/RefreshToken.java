package com.brenoleal.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.brenoleal.commons.UseCase;
import com.brenoleal.core.Papel;
import com.brenoleal.core.Usuario;
import com.brenoleal.service.IUsuarioService;
import com.brenoleal.util.JWTUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RefreshToken extends UseCase<Void> {

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public RefreshToken(HttpServletResponse response, HttpServletRequest request){
        this.request = request;
        this.response = response;
    }

    @Inject
    private IUsuarioService usuarioService;

    @Override
    protected Void execute() throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
                String login = decodedJWT.getSubject();
                Usuario usuario = usuarioService.findByLogin(login);
                List<String> claims = usuario.getPapeis().stream().map(Papel::getNome).collect(Collectors.toList());
                String access_token = JWTUtil.generateAccessToken(usuario.getLogin(), claims, request.getRequestURL().toString(), 10);
                JWTUtil.writeTokenResponse(access_token, token, response);
            } catch (Exception ex) {
                JWTUtil.writeErrorResponse(response, ex);
            }
        } else {
            throw new RuntimeException("Não foi possível encontrar o refresh token");
        }
        return null;
    }
}
