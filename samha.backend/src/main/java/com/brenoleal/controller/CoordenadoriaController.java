package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Coordenadoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/coordenadoria")
public class CoordenadoriaController extends BaseController<Coordenadoria, Integer> {
    public CoordenadoriaController(UseCaseFacade facade) {
        super(Coordenadoria.class, facade);
    }
}
