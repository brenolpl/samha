package com.samha.controller;

import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Alocacao;
import com.samha.domain.log.AlocacaoAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alocacao")
public class AlocacaoController extends BaseController<Alocacao, AlocacaoAud, Integer> {
    public AlocacaoController(UseCaseFacade facade) {
        super(Alocacao.class, AlocacaoAud.class, facade);
    }
}
