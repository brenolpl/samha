package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.domain.EfetuarLogin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{

    private UseCaseFacade facade;

    public LoginController(UseCaseFacade facade){
        this.facade = facade;
    }

    @PostMapping("api/login")
    public Usuario login(@RequestBody UsuarioDto body){
        return this.facade.execute(new EfetuarLogin(body));
    }
}
