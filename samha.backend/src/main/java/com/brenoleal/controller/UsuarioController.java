package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.domain.IncluirUsuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private final UseCaseFacade facade;

    public UsuarioController(UseCaseFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/insert")
    public Usuario insert(@RequestBody UsuarioDto body){
        return this.facade.execute(new IncluirUsuario(body));
    }
}
