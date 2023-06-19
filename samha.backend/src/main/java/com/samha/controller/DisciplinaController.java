package com.samha.controller;

import com.samha.application.disciplina.GerarRelatorioDisciplina;
import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Disciplina;
import com.samha.domain.dto.RelatorioDto;
import com.samha.domain.log.DisciplinaAud;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/disciplina")
public class DisciplinaController extends BaseController<Disciplina, DisciplinaAud, Integer> {
    public DisciplinaController(UseCaseFacade facade) {
        super(Disciplina.class, DisciplinaAud.class, facade);
    }

    @PostMapping("gerar-relatorio")
    public Map<String, Object> gerarRelatorio(@RequestBody RelatorioDto relatorioDto) {
        return facade.execute(new GerarRelatorioDisciplina(relatorioDto));
    }
}
