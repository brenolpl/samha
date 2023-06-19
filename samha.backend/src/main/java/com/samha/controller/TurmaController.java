package com.samha.controller;


import com.samha.application.turma.AtualizarTurmasAtivas;
import com.samha.application.turma.GerarRelatorioTurmas;
import com.samha.application.turma.ObterAulasTurmaRelatorio;
import com.samha.application.turma.ObterPeriodoAtualTurma;
import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Turma;
import com.samha.domain.dto.RelatorioDto;
import com.samha.domain.log.TurmaAud;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/turma")
public class TurmaController extends BaseController<Turma, TurmaAud, Integer> {
    public TurmaController(UseCaseFacade facade) {
        super(Turma.class, TurmaAud.class, facade);
    }

    @PostMapping("atualizarTurmas")
    public Boolean atualizarTurmasAtivas(){
        return facade.execute(new AtualizarTurmasAtivas());
    }

    @GetMapping("getPeriodoAtual/{id}")
    public Integer getPeriodoAtual(@PathVariable Integer id) {
        return facade.execute(new ObterPeriodoAtualTurma(id));
    }

    @PostMapping("obter-aulas-turma")
    public List<Turma> obterAulasTurma(@RequestBody RelatorioDto relatorioDto) {
        return facade.execute(new ObterAulasTurmaRelatorio(relatorioDto));
    }

    @PostMapping("gerar-relatorio")
    public Map<String, Object> gerarRelatorio(@RequestBody RelatorioDto relatorioDto) {
        return facade.execute(new GerarRelatorioTurmas(relatorioDto));
    }
}
