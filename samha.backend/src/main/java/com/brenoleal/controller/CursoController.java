package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Curso;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/curso")
public class CursoController extends BaseController<Curso, Integer> {
    public CursoController(UseCaseFacade facade) {
        super(Curso.class, facade);
    }
}
