package com.samha.controller;

import com.samha.application.professor.GerarRelatorioProfessor;
import com.samha.application.professor.ObterAulasProfessores;
import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Professor;
import com.samha.domain.dto.RelatorioDto;
import com.samha.domain.log.ProfessorAud;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/professor")
public class ProfessorController extends BaseController<Professor, ProfessorAud, Integer> {
    public ProfessorController(UseCaseFacade facade) {
        super(Professor.class, ProfessorAud.class, facade);
    }

    @PostMapping("obter-professores-relatorio")
    public List<Professor> obterProfessoresRelatorio(@RequestBody RelatorioDto relatorioDto) {
        return facade.execute(new ObterAulasProfessores(relatorioDto));
    }

    @PostMapping("gerar-relatorio")
    public Map<String, Object> gerarRelatorio(@RequestBody RelatorioDto relatorioDto) {
        return facade.execute(new GerarRelatorioProfessor(relatorioDto));
    }
}
