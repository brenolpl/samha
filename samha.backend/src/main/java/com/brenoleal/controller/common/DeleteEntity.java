package com.brenoleal.controller.common;

import com.brenoleal.commons.UseCase;
import com.brenoleal.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.io.Serializable;

public class DeleteEntity<ENTITY, KEY extends Serializable> extends UseCase<Void> {

    private final Class<ENTITY> entityClass;
    private final KEY id;

    public DeleteEntity(Class<ENTITY> entityClass, KEY id){
        this.entityClass = entityClass;
        this.id = id;
    }

    @Inject
    private IGenericRepository genericRepository;


    @Override
    protected Void execute() throws Exception {
        ENTITY toDelete = this.genericRepository.get(entityClass, id);
        this.genericRepository.delete(toDelete);
        return null;
    }
}
