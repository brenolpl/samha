package com.samha.application.aula;

import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.persistence.IAulaRepository;

import javax.inject.Inject;
import java.util.List;

public class SalvarAulas extends UseCase<List<Aula>> {

    private List<Aula> aulas;

    @Inject
    public SalvarAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }
    @Inject
    private IAulaRepository aulaRepository;

    @Override
    protected List<Aula> execute() throws Exception {
        aulaRepository.deleteAll(aulaRepository.getAulasByOferta_Id(aulas.get(0).getOferta().getId()));
        return aulaRepository.saveAll(aulas);
    }
}
