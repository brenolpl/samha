package com.samha.application.commons;

import com.samha.commons.UseCase;
import com.samha.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.util.List;

public class GetAll<ENTITY> extends UseCase<List<ENTITY>> {

    private Class<ENTITY> entityClass;

    public GetAll(Class<ENTITY> entityClass){
        this.entityClass = entityClass;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<ENTITY> execute() throws Exception {
        return this.genericRepository.findAll(entityClass);
    }
}
