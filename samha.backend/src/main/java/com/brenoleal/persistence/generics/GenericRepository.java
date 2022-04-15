package com.brenoleal.persistence.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
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

    @Override
    public <ENTITY> List<ENTITY> find(Class<ENTITY> entityClass, IQueryBuilder<ENTITY, ENTITY> queryBuilder) {
        return find(entityClass, entityClass, queryBuilder);
    }

    @Override
    public <ENTITY, TARGET> List<TARGET> find(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder<ENTITY, TARGET> queryBuilder) {
        CriteriaQuery<TARGET> query = this.createCriteriaQuery(entityClass, targetClass, queryBuilder);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <ENTITY> ENTITY findSingle(Class<ENTITY> entityClass, IQueryBuilder<ENTITY, ENTITY> queryBuilder) {
        return findsingle(entityClass, entityClass, queryBuilder);
    }

    @Override
    public <ENTITY, TARGET> TARGET findsingle(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder<ENTITY, TARGET> queryBuilder) {
        CriteriaQuery<TARGET> query = this.createCriteriaQuery(entityClass, targetClass, queryBuilder);
        List<TARGET> resultList = entityManager.createQuery(query).setMaxResults(1).getResultList();
        if(resultList.isEmpty()) return null;
        return resultList.get(0);
    }


    private <ENTITY, TARGET> CriteriaQuery<TARGET> createCriteriaQuery(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder queryBuilder){
        return queryBuilder.build(new QueryHelper<>(entityManager, entityClass, targetClass)).getCriteriaQuery();
    }
}
