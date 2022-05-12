package com.brenoleal.controller;


import com.brenoleal.application.turma.ConsultarTurmas;
import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.Turma;
import com.brenoleal.domain.log.TurmaAud;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/turma")
public class TurmaController extends BaseController<Turma, TurmaAud, Integer> {
    public TurmaController(UseCaseFacade facade) {
        super(Turma.class, TurmaAud.class, facade);
    }

    @Override
    public PagedList buildQueryEntities(Query query) {
        return this.facade.execute(new ConsultarTurmas(query));
    }
}
