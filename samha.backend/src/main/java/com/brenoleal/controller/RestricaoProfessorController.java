package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.RestricaoProfessor;
import com.brenoleal.domain.log.RestricaoProfessorAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/restricaoProfessor")
public class RestricaoProfessorController extends BaseController<RestricaoProfessor, RestricaoProfessorAud, Integer> {
    public RestricaoProfessorController(UseCaseFacade facade) {
        super(RestricaoProfessor.class, RestricaoProfessorAud.class, facade);
    }
}
