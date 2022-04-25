package com.brenoleal.controller.common;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.domain.commons.*;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public abstract class BaseController<ENTITY, KEY extends Serializable> {

    private final Class<ENTITY> entityClass;
    protected final UseCaseFacade facade;

    public BaseController(Class<ENTITY> entityClass, UseCaseFacade facade){
        this.facade = facade;
        Assert.notNull(entityClass, "EntityClass can not be null");
        this.entityClass = entityClass;
    }


    @PostMapping("query")
    public PagedList query(@RequestBody Query query){
        return this.facade.execute(new QueryEntities<>(query, this.entityClass));
    }

    @GetMapping("all")
    public List<ENTITY> getAll(){
        return this.facade.execute(new GetAll<>(this.entityClass));
    }

    @GetMapping("{id}")
    public ENTITY get(@PathVariable KEY id){
        return this.facade.execute(new GetEntity<>(this.entityClass, id));
    }

    @PostMapping
    public ENTITY insert(@RequestBody ENTITY body){
        return this.facade.execute(new InsertEntity<>(body));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable KEY id){
        this.facade.execute(new DeleteEntity<>(entityClass, id));
    }

    @PatchMapping("{id}")
    public ENTITY update(@PathVariable KEY id, @RequestBody ENTITY entity){
        return this.facade.execute(new UpdateEntity<>(entity));
    }
}
