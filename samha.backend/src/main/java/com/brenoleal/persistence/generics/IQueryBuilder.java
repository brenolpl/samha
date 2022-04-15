package com.brenoleal.persistence.generics;

@FunctionalInterface
public interface IQueryBuilder<ENTITY, TARGET> {
    IQueryHelper<ENTITY, TARGET> build(IQueryHelper<ENTITY, TARGET> helper);
}
