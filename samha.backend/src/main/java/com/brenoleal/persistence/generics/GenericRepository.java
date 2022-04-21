package com.brenoleal.persistence.generics;

import com.brenoleal.commons.UseCase;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GenericRepository implements IGenericRepository{

    @PersistenceContext
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
    public <ENTITY, TARGET> PagedList<TARGET> find(Class<ENTITY> entityClass, Class<TARGET> targetClass, Page page, IQueryBuilder<ENTITY, TARGET> queryBuilder) {
        if (page == null)
            return new PagedList<>(this.find(entityClass, targetClass, queryBuilder));

        // Execute Base Criteria Query
        CriteriaQuery<TARGET> criteriaQuery = this.createCriteriaQuery(entityClass, targetClass, queryBuilder);

        page.setTotalItems(PersistenceHelper.count(this.entityManager, criteriaQuery).intValue());

        if (page.getTotalItems() == 0) return new PagedList<>(new ArrayList<TARGET>(), page);

        List<TARGET> list = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getSkip() == null ? page.getSize() * page.getNumber() : page.getSkip())
                .setMaxResults(page.getSize())
                .getResultList();

        return new PagedList<>(list, page);
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
