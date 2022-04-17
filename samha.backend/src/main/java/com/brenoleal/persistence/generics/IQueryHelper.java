package com.brenoleal.persistence.generics;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

public interface IQueryHelper<ENTITY, TARGET> {

    CriteriaBuilder getCriteriaBuilder();

    CriteriaQuery<TARGET> getCriteriaQuery();

    Root<ENTITY> getRoot();

    IQueryHelper<ENTITY, TARGET> where(Predicate... restrictions);

    Predicate equal(Expression<?> var1, Object var2);

    Predicate notEqual(Expression<?> var1, Object var2);

    Predicate or(Predicate... var1);

    <T> Predicate in(Expression<? extends T> expression);

    <Y> Path<Y> get(SingularAttribute<? super ENTITY, Y> attribute);

}
