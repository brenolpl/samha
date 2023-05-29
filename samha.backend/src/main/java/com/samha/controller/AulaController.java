package com.samha.controller;

import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Aula;
import com.samha.domain.log.AulaAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/aula")
public class AulaController extends BaseController<Aula, AulaAud, Integer> {
    public AulaController(UseCaseFacade facade) {
        super(Aula.class, AulaAud.class, facade);
    }
}
