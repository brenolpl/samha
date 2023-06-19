
package com.samha.application.turma;

import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.Turma;
import com.samha.domain.dto.AulaDto;
import com.samha.domain.dto.RelatorioDto;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.util.JasperHelper;
import com.samha.util.Zipper;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Async
public class GerarRelatorioTurmas extends UseCase<Map<String, Object>> {

    private RelatorioDto relatorioDto;

    @Inject
    public GerarRelatorioTurmas(RelatorioDto relatorioDto) {
        this.relatorioDto = relatorioDto;
        this.addBefore(new AtualizarTurmasAtivas());
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Map<String, Object> execute() throws Exception {
        ObterAulasTurmaRelatorio obterAulasTurmaRelatorio = new ObterAulasTurmaRelatorio(relatorioDto, genericRepository);
        List<Turma> turmas = obterAulasTurmaRelatorio.execute().stream().sorted(Comparator.comparing(Turma::getNome)).collect(Collectors.toList());
        List<Map<String, Object>> reports = new ArrayList<>();
        for (var turma : turmas) {
            String nomeTurma = turma.getNome();
            //zip entry error com turmas de mesmo nome.
            List<Turma> turmasComMesmoNome = turmas.stream().filter(t -> t.getNome().equals(turma.getNome())).collect(Collectors.toList());
            if (turmasComMesmoNome.size() > 1) {
                nomeTurma += LocalTime.now().getNano();
            }
            Map<String, String> parametros = gerarHashTurma(turma);
            parametros.putAll(this.preencherAulas(turma));
            Map<String, Object> arquivo = new HashMap<>();
            String nomeExport = nomeTurma + "-" + relatorioDto.getAno() + "-" + relatorioDto.getSemestre() + ".pdf";
            arquivo.put("nome", nomeExport);
            arquivo.put("bytes", JasperHelper.generateReport(parametros, relatorioDto));
            reports.add(arquivo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("bytes", Zipper.createZipFile(reports));
        return result;

    }


    private Map<String, String> preencherAulas(Turma turma) {
        Map<String, String> hashMapAulas = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 16; j++) {
                final int line = i;
                final int column = j;
                Optional<AulaDto> aulaMatriz = turma.getAulas().stream().filter(a -> a.getDia() == line && a.getNumero() == column).findFirst();
                if (aulaMatriz.isPresent()) {
                    Aula aula = genericRepository.get(Aula.class, aulaMatriz.get().getId());
                    String professor = aula.getAlocacao().getProfessor1().obterNomeAbreviado() + this.getProfessor2String(aula);
                    String key = aula.getDia() + String.valueOf(aula.getNumero());
                    String sigla = aula.getAlocacao().getDisciplina().getNome();
                    hashMapAulas.put(key, professor + "\n" + sigla);
                } else {
                    String key = line + String.valueOf(column);
                    String professor = "";
                    String sigla = "";
                    hashMapAulas.put(key, professor + "\n" + sigla);
                }
            }
        }
        return hashMapAulas;
    }

    private String getProfessor2String(Aula aula) {
        if (aula.getAlocacao().getProfessor2() != null) return aula.getAlocacao().getProfessor2().obterNomeAbreviado();
        return "";
    }

    public Map<String, String> gerarHashTurma(Turma turma){

        Map<String, String> hash = new HashMap();

        String anoSemestre = relatorioDto.getAno() + "/" + relatorioDto.getSemestre();
        hash.put("nome", turma.getNome() + " - " + ObterPeriodoAtualTurma.getPeriodoAtual(turma));
        hash.put("aulas", anoSemestre);
        hash.put("setor", turma.getMatriz().getCurso().getNome());

        return hash;
    }
}
