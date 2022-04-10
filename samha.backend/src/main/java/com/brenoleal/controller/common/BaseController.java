package com.brenoleal.controller.common;

import com.brenoleal.commons.UseCaseFacade;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public abstract class BaseController<ENTITY, KEY extends Serializable> {

    private Class<ENTITY> entityClass;
    protected final UseCaseFacade facade;

    public BaseController(Class<ENTITY> entityClass, UseCaseFacade facade){
        this.facade = facade;
        Assert.notNull(entityClass, "EntityClass can not be null");
        this.entityClass = entityClass;
    }

    @GetMapping("{id}")
    public <ENTITY> ENTITY get(@PathVariable KEY id){
        return (ENTITY) this.facade.execute(new GetEntity(this.entityClass, id));
    }
}
