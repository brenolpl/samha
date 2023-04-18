package com.brenoleal.application.turma;

import com.brenoleal.commons.UseCase;
import com.brenoleal.domain.Turma;
import com.brenoleal.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ObterPeriodoAtualTurma extends UseCase<Integer> {

    private Integer id;

    @Inject
    public ObterPeriodoAtualTurma(Integer id) {
        this.id = id;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Integer execute() throws Exception {
        Turma turma = this.genericRepository.get(Turma.class, id);
        Integer anoAtual = LocalDateTime.now().getYear();
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO")) {
            int qtAnos = anoAtual - turma.getAno() + 1;
            return qtAnos;
        } else {
            int qtAnos = (anoAtual - turma.getAno()) * 2;
            int semestreAtual;
            if(Calendar.getInstance().get(Calendar.MONTH) >= Calendar.getInstance().get(Calendar.JULY)){
                semestreAtual = 2;
            }else{
                semestreAtual = 1;
            }

            if(semestreAtual == turma.getSemestre()) qtAnos += 1;
            if(semestreAtual == 2 && turma.getSemestre() == 1) qtAnos += 2;

            return qtAnos;
        }
    }
}
