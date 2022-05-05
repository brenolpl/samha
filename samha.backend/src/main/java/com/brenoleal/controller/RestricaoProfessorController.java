package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.RestricaoProfessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/restricaoProfessor")
public class RestricaoProfessorController extends BaseController<RestricaoProfessor, Integer> {
    public RestricaoProfessorController(UseCaseFacade facade) {
        super(RestricaoProfessor.class, facade);
    }
}
