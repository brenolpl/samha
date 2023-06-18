package com.samha.application.aula;

import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.dto.Conflito;
import com.samha.domain.dto.ConflitoTurma;
import com.samha.domain.Oferta_;
import com.samha.domain.Turma;
import com.samha.domain.Turma_;
import com.samha.persistence.generics.IGenericRepository;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Async
public class ObterConflitosTurmas extends UseCase<List<ConflitoTurma>> {

    private Integer ano;
    private Integer semestre;
    private List<ConflitoTurma> conflitoTurmas = new ArrayList<>();

    @Inject
    public ObterConflitosTurmas(Integer ano, Integer semestre) {
        this.ano = ano;
        this.semestre = semestre;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<ConflitoTurma> execute() throws Exception {
        List<Turma> turmas = genericRepository.find(Turma.class, q -> q.where(
           q.equal(q.get(Turma_.ativa), true)
        )).stream().sorted(Comparator.comparing(Turma::getNome)).collect(Collectors.toList());

        for(var turma : turmas) {
            List<Aula> aulasTurma = genericRepository.find(Aula.class, q -> q.where(
                    q.equal(q.get(Aula_.oferta).get(Oferta_.ano), ano),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), semestre),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.turma), turma)
            ));

            if (!aulasTurma.isEmpty()) {
                ObterRestricoesAulas restricoesUseCase = new ObterRestricoesAulas(aulasTurma, genericRepository);
                List<Conflito> conflitos = restricoesUseCase.execute();
                if (!conflitos.isEmpty()) {
                    ConflitoTurma novoConflito = new ConflitoTurma();
                    novoConflito.setConflitos(conflitos);
                    novoConflito.setTurma(turma);
                    conflitoTurmas.add(novoConflito);
                }
            }
        }


        return conflitoTurmas;
    }
}
