package com.samha.persistence.generics;

import com.samha.persistence.filter.Page;
import com.samha.persistence.filter.PagedList;
import org.hibernate.envers.AuditReader;

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

    <ENTITY, TARGET> PagedList<TARGET> find(Class<ENTITY> entityClass, Class<TARGET> targetClass, Page page, IQueryBuilder<ENTITY, TARGET> queryBuilder);

    <ENTITY> ENTITY findSingle(Class<ENTITY> entityClass, IQueryBuilder<ENTITY, ENTITY> queryBuilder);

    <ENTITY, TARGET> TARGET findsingle(Class<ENTITY> entityClass, Class<TARGET> targetClass, IQueryBuilder<ENTITY, TARGET> queryBuilder);

    AuditReader getReader();
}
