package com.brenoleal.domain.commons;

import com.brenoleal.commons.UseCase;
import com.brenoleal.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.io.Serializable;

public class GetEntity<ENTITY, KEY extends Serializable> extends UseCase<ENTITY> {

    private final Class<ENTITY> entityClass;
    private final KEY id;

    @Inject
    protected IGenericRepository genericRepository;

    public GetEntity(Class<ENTITY> entityClass, KEY id) {
        this.entityClass = entityClass;
        this.id = id;
    }

    @Override
    protected ENTITY execute() throws Exception {
        return genericRepository.get(this.entityClass, this.id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + entityClass.getSimpleName();
    }
}
