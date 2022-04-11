package com.brenoleal.domain.legacy;

import com.brenoleal.commons.UseCase;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.persistence.IGenericRepository;

import javax.inject.Inject;

public class EfetuarLogin extends UseCase<Usuario> {

    private UsuarioDto usuarioDto;

    public EfetuarLogin(UsuarioDto usuarioDto){
        this.usuarioDto = usuarioDto;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Usuario execute() throws Exception {

        return null;
    }
}
