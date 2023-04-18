package com.samha.controller;

import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Disciplina;
import com.samha.domain.log.DisciplinaAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/disciplina")
public class DisciplinaController extends BaseController<Disciplina, DisciplinaAud, Integer> {
    public DisciplinaController(UseCaseFacade facade) {
        super(Disciplina.class, DisciplinaAud.class, facade);
    }
}
