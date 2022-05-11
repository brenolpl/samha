package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Eixo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/eixo")
public class EixoController extends BaseController<Eixo, Integer> {

    public EixoController(UseCaseFacade facade) {
        super(Eixo.class, facade);
    }
}
