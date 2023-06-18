package com.samha.application.aula;

import com.samha.commons.CorEnum;
import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.dto.Conflito;
import com.samha.domain.Disciplina;
import com.samha.domain.dto.Mensagem;
import com.samha.domain.Professor;
import com.samha.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ObterConflitosQtdDisciplina extends UseCase<List<Conflito>> {

    private List<Aula> aulas;
    private List<Conflito> conflitos = new ArrayList<>();

    @Inject
    public ObterConflitosQtdDisciplina(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<Conflito> execute() throws Exception {
        final Set<Integer> disciplinasId = aulas.stream().map(a -> a.getAlocacao().getDisciplina().getId()).collect(Collectors.toSet());

        for (Integer id : disciplinasId) {
            Disciplina disciplina = genericRepository.get(Disciplina.class, id);
            List<Aula> aulasDisciplina = aulas.stream().filter(a -> a.getAlocacao().getDisciplina().getId().equals(id)).collect(Collectors.toList());

            if (disciplina.getQtAulas() != aulasDisciplina.size()) {
                Conflito conflito = new Conflito();
                Mensagem mensagem = new Mensagem();
                mensagem.setTipo(2);
                mensagem.setCor(CorEnum.AMARELO.getId());
                mensagem.setTitulo("Quantidade de aulas diferente da especificada");
                List<String> restrições = new ArrayList<>();
                restrições.add("Disciplina: " + disciplina.getSigla());
                restrições.add("Aulas alocadas: " + aulasDisciplina.size());
                restrições.add("Quantidade especificada: " + disciplina.getQtAulas());
                mensagem.setRestricoes(restrições);
                conflito.getMensagens().add(mensagem);
                //Feito dessa forma, pois, este tipo de notificação não possui professor atrelado.
                //Para não mudar toda a lógica de processamento dos conflitos, foi criado um professor fake para aparecer como título do card.
                Professor fake = new Professor();
                fake.setNome("Quantidade de aulas diferente da especificada");
                conflito.setProfessor(fake);
                conflitos.add(conflito);
            }
        }
        return conflitos;
    }
}
