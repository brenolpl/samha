package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Disciplina;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/disciplina")
public class DisciplinaController extends BaseController<Disciplina, Integer> {
    public DisciplinaController(UseCaseFacade facade) {
        super(Disciplina.class, facade);
    }
}
