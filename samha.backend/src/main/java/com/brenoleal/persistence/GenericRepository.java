package com.brenoleal.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
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

    @Override
    public <ENTITY, KEY extends Serializable> ENTITY get(Class<ENTITY> entityClass, KEY id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public <ENTITY> ENTITY save(ENTITY entityClass) {
        entityManager.persist(entityClass);
        return entityClass;
    }

    @Override
    public <ENTITY> void delete(ENTITY entityClass) {
        entityManager.remove(entityClass);
    }

    @Override
    public <ENTITY> ENTITY update(ENTITY entity) {
        return entityManager.merge(entity);
    }


}
