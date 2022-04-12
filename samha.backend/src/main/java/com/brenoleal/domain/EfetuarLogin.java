package com.brenoleal.domain;

import com.brenoleal.commons.UseCase;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.UsuarioDto;
import com.brenoleal.persistence.IGenericRepository;

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
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Usuario execute() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> root = query.from(Usuario.class);

        query.where(
            builder.equal(root.get("login"), this.usuarioDto.getLogin()),
            builder.equal(root.get("senha"), this.usuarioDto.getSenha())
        );

        TypedQuery<Usuario> usuarioQuery = entityManager.createQuery(query);

        List<Usuario> usuarioList = usuarioQuery.getResultList();

        if(usuarioList.isEmpty() || usuarioList == null){
            throw new RuntimeException("Não foi possível fazer login");
        }

        return usuarioList.get(0);
    }
}
