package com.brenoleal.persistence.generics;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

public class QueryHelper<ENTITY, TARGET> implements IQueryHelper<ENTITY, TARGET>{

    private EntityManager entityManager;
    private CriteriaBuilder builder;
    private CriteriaQuery<TARGET> query;
    private Class<ENTITY> entityClass;
    private Class<TARGET> targetClass;
    private Root<ENTITY> root;

    public QueryHelper(EntityManager entityManager, Class<ENTITY> entityClass, Class<TARGET> targetClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.targetClass = targetClass;

        this.builder = this.entityManager.getCriteriaBuilder();
        this.query = this.builder.createQuery(targetClass);
        this.root = this.query.from(entityClass);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return this.builder;
    }

    @Override
    public CriteriaQuery getCriteriaQuery() {
        return this.query;
    }

    @Override
    public Root<ENTITY> getRoot() {
        return this.root;
    }

    @Override
    public IQueryHelper<ENTITY, TARGET> where(Predicate... restrictions) {
        if(restrictions != null && restrictions[0] != null) this.query.where(restrictions);
        return this;
    }

    @Override
    public Predicate equal(Expression<?> var1, Object var2) {
        if(var2 == null) return builder.and();
        return builder.equal(var1, var2);
    }

    @Override
    public Predicate notEqual(Expression<?> var1, Object var2) {
        if(var2 == null) return builder.and();
        return builder.notEqual(var1, var2);
    }

    @Override
    public Predicate or(Predicate... var1) {
        if(var1 == null) return builder.and();
        return builder.or(var1);
    }

    @Override
    public <T> Predicate in(Expression<? extends T> expression) {
        return builder.in(expression);
    }

    @Override
    public <Y> Path<Y> get(SingularAttribute<? super ENTITY, Y> attribute) {
        return root.get(attribute);
    }
}
