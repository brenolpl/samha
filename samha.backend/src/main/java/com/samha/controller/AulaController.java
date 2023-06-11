package com.samha.controller;

import com.samha.application.aula.ObterRestricoesAulas;
import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Aula;
import com.samha.domain.Conflito;
import com.samha.domain.log.AulaAud;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/aula")
public class AulaController extends BaseController<Aula, AulaAud, Integer> {
    public AulaController(UseCaseFacade facade) {
        super(Aula.class, AulaAud.class, facade);
    }

    @PostMapping("obter-restricoes")
    public List<Conflito> obterRestricoes(@RequestBody List<Aula> aulas) {
        return facade.execute(new ObterRestricoesAulas(aulas));
    }
}
