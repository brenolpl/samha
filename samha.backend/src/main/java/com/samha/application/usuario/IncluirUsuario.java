package com.samha.application.usuario;


import com.samha.commons.UseCase;
import com.samha.domain.Papel;
import com.samha.domain.Servidor;
import com.samha.domain.Usuario;
import com.samha.domain.UsuarioDto;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.service.IUsuarioService;

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
