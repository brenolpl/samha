package com.brenoleal.controller;

import com.brenoleal.application.IncluirUsuario;
import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.domain.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController extends BaseController<Usuario, Integer> {


    public UsuarioController(UseCaseFacade facade) {
        super(Usuario.class, facade);
    }

    @PostMapping("/newUser")
    public Usuario insert(@RequestBody UsuarioDto body){
        return this.facade.execute(new IncluirUsuario(body));
    }

}
