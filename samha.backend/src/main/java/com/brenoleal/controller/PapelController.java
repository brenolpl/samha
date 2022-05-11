package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Papel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/papel")
public class PapelController extends BaseController<Papel, Integer> {
    public PapelController(UseCaseFacade facade) {
        super(Papel.class, facade);
    }
}
