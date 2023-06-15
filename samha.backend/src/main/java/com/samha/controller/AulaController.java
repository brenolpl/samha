package com.samha.controller;

import com.samha.application.aula.ObterConflitosQtdDisciplina;
import com.samha.application.aula.ObterConflitosTurmas;
import com.samha.application.aula.ObterRestricoesAulas;
import com.samha.application.aula.SalvarAulas;
import com.samha.commons.UseCaseFacade;
import com.samha.controller.common.BaseController;
import com.samha.domain.Aula;
import com.samha.domain.Conflito;
import com.samha.domain.ConflitoTurma;
import com.samha.domain.log.AulaAud;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/aula")
public class AulaController extends BaseController<Aula, AulaAud, Integer> {
    public AulaController(UseCaseFacade facade) {
        super(Aula.class, AulaAud.class, facade);
    }

    @PostMapping("obter-restricoes")
    public List<Conflito> obterRestricoes(@RequestBody List<Aula> aulas) {
        return facade.execute(new ObterRestricoesAulas(aulas));
    }

    @PostMapping("controle-qtd-disciplina")
    public List<Conflito> obterConflitosQtdDisciplina(@RequestBody List<Aula> aulas) {
        return facade.execute(new ObterConflitosQtdDisciplina(aulas));
    }

    @PostMapping("salvar-aulas")
    public List<Aula> salvarAulas(@RequestBody List<Aula> aulas) {
        return facade.execute(new SalvarAulas(aulas));
    }

    @PostMapping("validar-turmas/{ano}/{semestre}")
    public List<ConflitoTurma> getValidacaoTurmas(@PathVariable Integer ano, @PathVariable Integer semestre) {
        return facade.execute(new ObterConflitosTurmas(ano, semestre));
    }
}
