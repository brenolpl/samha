package com.brenoleal.commons;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class UseCase<ENTITY> implements Serializable {

    private final List<UseCase> executeBefore = new ArrayList<>();
    private final List<UseCase> executeAfter = new ArrayList<>();

    protected abstract ENTITY execute() throws Exception;

    protected void addBefore(UseCase useCase){
        this.executeBefore.add(useCase);
    }

    protected void addAfter(UseCase useCase){
        this.executeAfter.add(useCase);
    }
}
