package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Papel;
import com.brenoleal.domain.log.PapelAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/papel")
public class PapelController extends BaseController<Papel, PapelAud, Integer> {
    public PapelController(UseCaseFacade facade) {
        super(Papel.class, PapelAud.class, facade);
    }
}
