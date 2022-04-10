package com.brenoleal.samha.persistence;

import java.util.List;

public interface IGenericRepository {

    <ENTITY> List<ENTITY> findAll(Class<ENTITY> entityClass);

}
