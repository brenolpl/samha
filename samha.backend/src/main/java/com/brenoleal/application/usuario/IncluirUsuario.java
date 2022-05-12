package com.brenoleal.application.usuario;


import com.brenoleal.commons.UseCase;
import com.brenoleal.domain.Papel;
import com.brenoleal.domain.Servidor;
import com.brenoleal.domain.Usuario;
import com.brenoleal.domain.UsuarioDto;
import com.brenoleal.persistence.generics.IGenericRepository;
import com.brenoleal.service.IUsuarioService;

import javax.inject.Inject;

public class IncluirUsuario extends UseCase<Usuario> {

    private UsuarioDto usuarioDto;

    public IncluirUsuario(UsuarioDto usuario){
        this.usuarioDto = usuario;
    }

    @Inject
    private IUsuarioService usuarioService;

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Usuario execute() throws Exception {
        Usuario user = new Usuario();
        user.setLogin(this.usuarioDto.getLogin());
        user.setSenha(this.usuarioDto.getSenha());
        Papel papel = this.genericRepository.get(Papel.class, this.usuarioDto.getPapel_id());
        user.setPapel(papel);
        user = this.usuarioService.saveUsuario(user);

        if(this.usuarioDto.getServidor_id() != null){
            Servidor servidor = this.genericRepository.get(Servidor.class, this.usuarioDto.getServidor_id());
            servidor.setUsuario(user);
            this.genericRepository.save(servidor);
        }
        return new Usuario();
    }
}