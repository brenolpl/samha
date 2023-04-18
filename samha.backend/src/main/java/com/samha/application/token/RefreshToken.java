package com.samha.application.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.samha.commons.UseCase;
import com.samha.domain.Usuario;
import com.samha.service.IUsuarioService;
import com.samha.util.JWTUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
                List<String> claims = new ArrayList<>();
                claims.add(usuario.getPapel().getNome());
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
