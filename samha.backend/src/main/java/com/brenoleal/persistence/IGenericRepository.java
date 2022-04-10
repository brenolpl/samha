package com.brenoleal.persistence;

import java.io.Serializable;
import java.util.List;

public interface IGenericRepository {

    <ENTITY> List<ENTITY> findAll(Class<ENTITY> entityClass);
    <ENTITY, KEY extends Serializable> ENTITY get(Class<ENTITY> entityClass, KEY id);
    <ENTITY> ENTITY save(ENTITY entityClass);
    <ENTITY> void delete(ENTITY entityClass);
    <ENTITY> ENTITY update(ENTITY entity);
}
