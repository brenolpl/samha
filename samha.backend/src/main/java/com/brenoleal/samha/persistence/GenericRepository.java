package com.brenoleal.samha.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenericRepository implements IGenericRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public <ENTITY> List<ENTITY> findAll(Class<ENTITY> entityClass){
        String queryBase = "from " + entityClass.getName();
        TypedQuery<ENTITY> query = entityManager.createQuery(queryBase, entityClass);
        return query.getResultList();
    }
}
