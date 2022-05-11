package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.core.Turma;
import com.brenoleal.core.TurmaDto;
import com.brenoleal.domain.ConsultarTurmas;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/turma")
public class TurmaController extends BaseController<Turma, Integer> {
    public TurmaController(UseCaseFacade facade) {
        super(Turma.class, facade);
    }

    @Override
    public PagedList buildQueryEntities(Query query) {
        return this.facade.execute(new ConsultarTurmas(query));
    }
}
