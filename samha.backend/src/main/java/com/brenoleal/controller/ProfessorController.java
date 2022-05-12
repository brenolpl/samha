package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Professor;
import com.brenoleal.domain.log.ProfessorAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/professor")
public class ProfessorController extends BaseController<Professor, ProfessorAud, Integer> {
    public ProfessorController(UseCaseFacade facade) {
        super(Professor.class, ProfessorAud.class, facade);
    }
}
