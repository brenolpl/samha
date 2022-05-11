package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.Curso;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/curso")
public class CursoController extends BaseController<Curso, Integer> {
    public CursoController(UseCaseFacade facade) {
        super(Curso.class, facade);
    }
}
