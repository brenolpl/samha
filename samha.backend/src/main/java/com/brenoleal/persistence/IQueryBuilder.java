package com.brenoleal.persistence;

import java.util.List;

@FunctionalInterface
public interface IQueryBuilder<ENTITY, TARGET> {
    IQueryHelper<ENTITY, TARGET> build(IQueryHelper<ENTITY, TARGET> helper);
}
