package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.Servidor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/servidor")
public class ServidorController extends BaseController<Servidor, Integer> {
    public ServidorController(UseCaseFacade facade) {
        super(Servidor.class, facade);
    }
}
