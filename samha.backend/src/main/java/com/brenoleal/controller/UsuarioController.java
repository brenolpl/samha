package com.brenoleal.controller;


import com.brenoleal.application.usuario.IncluirUsuario;
import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Usuario;
import com.brenoleal.domain.UsuarioDto;
import com.brenoleal.domain.log.UsuarioAud;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController extends BaseController<Usuario, UsuarioAud, Integer> {


    public UsuarioController(UseCaseFacade facade) {
        super(Usuario.class, UsuarioAud.class, facade);
    }

    @PostMapping("/newUser")
    public Usuario insert(@RequestBody UsuarioDto body){
        return this.facade.execute(new IncluirUsuario(body));
    }

}
