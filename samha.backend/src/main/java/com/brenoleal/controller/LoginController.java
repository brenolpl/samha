package com.brenoleal.controller;

import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{


    @PostMapping("api/login")
    public Usuario login(@RequestBody UsuarioDto body){
        System.out.println("test");
        return null;
    }
}
