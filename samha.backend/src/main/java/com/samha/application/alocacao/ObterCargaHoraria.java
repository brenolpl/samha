package com.samha.application.alocacao;

import com.samha.commons.UseCase;
import com.samha.domain.Alocacao;
import com.samha.domain.Alocacao_;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Coordenadoria_;
import com.samha.domain.Eixo_;
import com.samha.domain.Professor;
import com.samha.domain.Professor_;
import com.samha.persistence.IProfessorRepository;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.util.Horarios;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ObterCargaHoraria extends UseCase<List<Professor>> {

    private Integer ano;
    private Integer semestre;
    private Integer eixoId;

    @Inject
    public ObterCargaHoraria(HashMap<String, String> params) {
        this.ano = Integer.parseInt(params.get("ano"));
        this.semestre = Integer.parseInt(params.get("semestre"));
        this.eixoId = Integer.parseInt(params.get("eixoId"));
    }

    public ObterCargaHoraria(IGenericRepository genericRepository, Map<String, String> params) {
        this.ano = Integer.parseInt(params.get("ano"));
        this.semestre = Integer.parseInt(params.get("semestre"));
        this.eixoId = Integer.parseInt(params.get("eixoId"));
        this.genericRepository = genericRepository;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Inject
    private IProfessorRepository professorRepository;

    @Override
    protected List<Professor> execute() throws Exception {
        List<Professor> professores = genericRepository.find(Professor.class, q -> q.where(
                q.equal(q.get(Professor_.coordenadoria).get(Coordenadoria_.eixo).get(Eixo_.id), eixoId),
                q.equal(q.get(Professor_.ativo), true)
        )).stream().sorted(Comparator.comparing(Professor::getNome)).collect(Collectors.toList());

        for (var professor : professores) {
            List<Alocacao> alocacoesProfessor = genericRepository.find(Alocacao.class, q -> q.where(
                    q.or(
                            q.equal(q.get(Alocacao_.professor1), professor),
                            q.equal(q.get(Alocacao_.professor2), professor)
                    ),
                    q.equal(q.get(Alocacao_.ano), ano),
                    q.equal(q.get(Alocacao_.semestre), semestre)
            ));
            Double tempoAula = 0D;
            for (var alocacao : alocacoesProfessor) {
                List<Aula> aulasAlocacao = genericRepository.find(Aula.class, q -> q.where(
                        q.equal(q.get(Aula_.alocacao), alocacao)
                ));
                for (var aula : aulasAlocacao) tempoAula += Horarios.obterQuantidadeHoras(aula, aula, Horarios.TEMPO_MAXIMO);
            }
            professor.setCargaHoraria(tempoAula);
            Double minutos = tempoAula - Math.floor(tempoAula);
            Long minutosArredondados = Math.round(minutos * 60);
            String cargaHoraria = (tempoAula.intValue() < 10 ? "0" + tempoAula.intValue() : tempoAula.intValue() )+ ":" + (minutosArredondados < 10 ? "0" + minutosArredondados : minutosArredondados);
            professor.setCargaHorariaCalculada(cargaHoraria);
        }
        professorRepository.saveAllAndFlush(professores);
        return professores;
    }
}
