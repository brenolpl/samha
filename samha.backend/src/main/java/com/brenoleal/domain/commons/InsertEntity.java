package com.brenoleal.domain.commons;

import com.brenoleal.commons.UseCase;
import com.brenoleal.persistence.generics.IGenericRepository;

import javax.inject.Inject;

public class InsertEntity<ENTITY> extends UseCase<ENTITY> {

    private final ENTITY entityClass;

    public InsertEntity(ENTITY entityClass){
        this.entityClass = entityClass;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected ENTITY execute() throws Exception {
        return this.genericRepository.save(entityClass);
    }
}
