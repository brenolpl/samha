package com.brenoleal.persistence.generics;

import java.io.Serializable;
import java.util.List;

public interface IGenericRepository {

    <ENTITY> List<ENTITY> findAll(Class<ENTITY> entityClass);

    <ENTITY, KEY extends Serializable> ENTITY get(Class<ENTITY> entityClass, KEY id);

    <ENTITY> ENTITY save(ENTITY entityClass);

    <ENTITY> void delete(ENTITY entityClass);

    <ENTITY> ENTITY update(ENTITY entity);

    <ENTITY> List<ENTITY> find(Class<ENTITY> entityClass, IQueryBuilder<ENTITY, ENTITY> queryBuilder);

    <ENTITY, TARGET> List<TARGET> find(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder<ENTITY, TARGET> queryBuilder);

    <ENTITY> ENTITY findSingle(Class<ENTITY> entityClass, IQueryBuilder<ENTITY, ENTITY> queryBuilder);

    <ENTITY, TARGET> TARGET findsingle(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder<ENTITY, TARGET> queryBuilder);
}