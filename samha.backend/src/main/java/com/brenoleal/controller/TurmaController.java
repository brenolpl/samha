package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.Turma;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/turma")
public class TurmaController extends BaseController<Turma, Integer> {
    public TurmaController(UseCaseFacade facade) {
        super(Turma.class, facade);
    }
}
