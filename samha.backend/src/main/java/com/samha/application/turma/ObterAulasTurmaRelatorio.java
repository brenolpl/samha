package com.samha.application.turma;

import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Coordenadoria_;
import com.samha.domain.Curso_;
import com.samha.domain.Eixo_;
import com.samha.domain.MatrizCurricular_;
import com.samha.domain.Oferta_;
import com.samha.domain.Turma;
import com.samha.domain.Turma_;
import com.samha.domain.dto.AulaDto;
import com.samha.domain.dto.RelatorioDto;
import com.samha.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ObterAulasTurmaRelatorio extends UseCase<List<Turma>> {

    private RelatorioDto relatorioDto;

    @Inject
    public ObterAulasTurmaRelatorio(RelatorioDto relatorioDto) {
        this.relatorioDto = relatorioDto;
    }

    public ObterAulasTurmaRelatorio(RelatorioDto relatorioDto, IGenericRepository genericRepository) {
        this.relatorioDto = relatorioDto;
        this.genericRepository = genericRepository;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<Turma> execute() throws Exception {
        List<Turma> turmas = new ArrayList<>();
        if (relatorioDto.getTurmaId() != null) {
            Turma turma = genericRepository.get(Turma.class, relatorioDto.getTurmaId());
            setAulasTurma(turma);
            turmas.add(turma);
            return turmas;
        } else if (relatorioDto.getCursoId() != null) {
            turmas = genericRepository.find(Turma.class, q -> q.where(
                    q.equal(q.get(Turma_.matriz).get(MatrizCurricular_.curso).get(Curso_.id), relatorioDto.getCursoId()),
                    q.equal(q.get(Turma_.ativa), true)
            ));
            turmas.forEach(t -> this.setAulasTurma(t));
            return turmas;
        } else if (relatorioDto.getEixoId() != null) {
            turmas = genericRepository.find(Turma.class, q -> q.where(
                    q.equal(q.get(Turma_.matriz).get(MatrizCurricular_.curso).get(Curso_.coordenadoria).get(Coordenadoria_.eixo).get(Eixo_.id), relatorioDto.getEixoId()),
                    q.equal(q.get(Turma_.ativa), true)
            ));
            turmas.forEach(t -> this.setAulasTurma(t));
            return turmas;
        } else {
            turmas = genericRepository.find(Turma.class, q -> q.where(
                    q.equal(q.get(Turma_.ativa), true)
            ));
            turmas.forEach(t -> setAulasTurma(t));
            return turmas;
        }
    }

    private void setAulasTurma(Turma turma) {
        List<Aula> aulas = genericRepository.find(Aula.class, q -> q.where(
                q.equal(q.get(Aula_.oferta).get(Oferta_.turma).get(Turma_.id), turma.getId()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), relatorioDto.getSemestre()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.ano), relatorioDto.getAno())
        ));

        List<AulaDto> aulasDto = new ArrayList<>();
        aulas.forEach(a -> aulasDto.add(new AulaDto(a.getId(), a.getDia(), a.getNumero(), getProfessores(a), a.getAlocacao().getDisciplina().getSigla(), a.getAlocacao().getDisciplina().getNome())));
        turma.setAulas(aulasDto);
    }

    private String getProfessores(Aula a) {
        String nomeProfessor = a.getAlocacao().getProfessor1().obterNomeAbreviado();
        if (a.getAlocacao().getProfessor2() != null) nomeProfessor += " " + a.getAlocacao().getProfessor2().obterNomeAbreviado();
        return nomeProfessor;
    }
}
