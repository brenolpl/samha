package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Coordenadoria;
import com.brenoleal.domain.log.CoordenadoriaAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/coordenadoria")
public class CoordenadoriaController extends BaseController<Coordenadoria, CoordenadoriaAud, Integer> {
    public CoordenadoriaController(UseCaseFacade facade) {
        super(Coordenadoria.class, CoordenadoriaAud.class, facade);
    }
}
