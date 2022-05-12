package com.brenoleal.controller;

import com.brenoleal.application.token.IsTokenValid;
import com.brenoleal.application.token.RefreshToken;
import com.brenoleal.commons.UseCaseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {

    private final UseCaseFacade facade;

    //TODO: Acrescentar no frontend a rota para utilizar o refresh_token

    @GetMapping("refreshToken")
    public void refreshToken(HttpServletResponse response, HttpServletRequest request) throws IOException {
        this.facade.execute(new RefreshToken(response, request));
    }

    @PostMapping("isTokenValid")
    public void isTokenValid(HttpServletResponse response, HttpServletRequest request) throws  IOException {
        this.facade.execute(new IsTokenValid(response, request));
    }
}
