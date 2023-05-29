package com.samha.controller;

import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Oferta;
import com.samha.domain.log.OfertaAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/oferta")
public class OfertaController extends BaseController<Oferta, OfertaAud, Integer> {
    public OfertaController(UseCaseFacade facade) {
        super(Oferta.class, OfertaAud.class, facade);
    }
}
