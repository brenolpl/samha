package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Professor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/professor")
public class ProfessorController extends BaseController<Professor, Integer> {
    public ProfessorController(UseCaseFacade facade) {
        super(Professor.class, facade);
    }
}
