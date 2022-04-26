package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.Professor;
import com.brenoleal.domain.ConsultarCoordenadores;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/coordenador")
public class CoordenadorController extends BaseController<Professor, Integer> {
    public CoordenadorController(UseCaseFacade facade) {
        super(Professor.class, facade);
    }

    @Override
    public PagedList buildQueryEntities(Query query) {
        return this.facade.execute(new ConsultarCoordenadores(query));
    }
}
