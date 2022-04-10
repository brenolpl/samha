package com.brenoleal.commons;

public interface IUseCaseManager {

    void prepare(UseCase useCase);

    void destroy(UseCase useCase);
}
