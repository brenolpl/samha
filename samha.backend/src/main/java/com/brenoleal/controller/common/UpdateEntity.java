package com.brenoleal.controller.common;

import com.brenoleal.commons.UseCase;
import com.brenoleal.persistence.IGenericRepository;

import javax.inject.Inject;

public class UpdateEntity<ENTITY> extends UseCase<ENTITY> {

    private final ENTITY entityClass;

    public UpdateEntity(ENTITY entityClass){
        this.entityClass = entityClass;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected ENTITY execute() throws Exception {
        return genericRepository.update(entityClass);
    }
}
