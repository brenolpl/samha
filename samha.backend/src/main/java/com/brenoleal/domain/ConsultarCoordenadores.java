package com.brenoleal.domain;

import com.brenoleal.core.Professor;
import com.brenoleal.domain.commons.QueryEntities;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;

import java.util.HashMap;
import java.util.Map;

public class ConsultarCoordenadores extends QueryEntities<Professor> {

    public ConsultarCoordenadores(Query query) {
        super(query, Professor.class);
    }

    @Override
    protected PagedList execute() throws Exception {
        this.buildCoordenadorPredicate();
        return super.execute();
    }

    private void buildCoordenadorPredicate() {
        Map<String, Object> papelNomeEqual = new HashMap<>();
        papelNomeEqual.put("equals", "COORDENADOR_CURSO");

        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("usuario.papel.nome", papelNomeEqual);

        this.entityQuery.getPredicates().put("and", usuarioMap);
    }
}
