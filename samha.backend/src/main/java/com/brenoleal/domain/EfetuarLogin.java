package com.brenoleal.domain;

import com.brenoleal.commons.UseCase;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.core.Usuario_;
import com.brenoleal.persistence.IGenericRepository;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EfetuarLogin extends UseCase<Usuario> {

    private UsuarioDto usuarioDto;

    public EfetuarLogin(UsuarioDto usuarioDto){
        this.usuarioDto = usuarioDto;
        Assert.notNull(usuarioDto.getLogin(), "Usuário não pode ser nulo");
        Assert.notNull(usuarioDto.getSenha(), "Senha não poder ser nulo");
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Usuario execute() throws Exception {
        Usuario usuario = this.genericRepository.findSingle(Usuario.class, q -> q.where(
            q.equal(q.get(Usuario_.login), usuarioDto.getLogin()),
            q.equal(q.get(Usuario_.senha), usuarioDto.getSenha())
        ));

        if(usuario == null){
            throw new RuntimeException("teste");
        }

        return usuario;
    }
}
