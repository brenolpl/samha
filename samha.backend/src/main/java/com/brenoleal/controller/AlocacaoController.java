package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Alocacao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alocacao")
public class AlocacaoController extends BaseController<Alocacao, Integer> {
    public AlocacaoController(UseCaseFacade facade) {
        super(Alocacao.class, facade);
    }
}
