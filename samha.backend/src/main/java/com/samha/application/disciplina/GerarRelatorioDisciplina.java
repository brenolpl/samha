package com.samha.application.disciplina;

import com.samha.application.turma.AtualizarTurmasAtivas;
import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Disciplina;
import com.samha.domain.Oferta_;
import com.samha.domain.Turma;
import com.samha.domain.Turma_;
import com.samha.domain.dto.RelatorioDto;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.util.JasperHelper;
import com.samha.util.Zipper;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Async
public class GerarRelatorioDisciplina extends UseCase<Map<String, Object>> {

    private RelatorioDto relatorioDto;

    @Inject
    public GerarRelatorioDisciplina(RelatorioDto relatorioDto) {
        this.relatorioDto = relatorioDto;
        this.addBefore(new AtualizarTurmasAtivas());
    }

    @Inject
    private IGenericRepository genericRepository;
    @Override
    protected Map<String, Object> execute() throws Exception {
        List<Turma> turmas = genericRepository.find(Turma.class, q -> q.where(
                q.equal(q.get(Turma_.ativa), true)
        ));
        List<Map<String, Object>> reports = new ArrayList<>();
        for (var turma : turmas) {
            String nomeTurma = turma.getNome();
            //zip entry error com turmas de mesmo nome.
            List<Turma> turmasComMesmoNome = turmas.stream().filter(t -> t.getNome().equals(turma.getNome())).collect(Collectors.toList());
            if (turmasComMesmoNome.size() > 1) {
                nomeTurma += LocalTime.now().getNano();
            }
            String mensagem = "\n\t\t\t\t\t\t" + nomeTurma + " - " + relatorioDto.getAno() + "/" + relatorioDto.getSemestre() + "\n\n\n";

            List<Aula> aulasTurma = genericRepository.find(Aula.class, q -> q.where(
                    q.equal(q.get(Aula_.oferta).get(Oferta_.turma), turma),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.ano), relatorioDto.getAno()),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), relatorioDto.getSemestre())
            ));
            mensagem = mensagem + obterSiglaDisciplina(aulasTurma) + "\n";


            String nomeExport = nomeTurma + "-Relatório de Disciplinas-" + relatorioDto.getAno() + "-" + relatorioDto.getSemestre() + ".pdf";

            List relatorio = new ArrayList();
            relatorio.add(new Aula());

            relatorioDto.setNomeRelatorio("relatorioDisciplinas");
            Map parametros =  new HashMap();
            parametros.put("mensagem", mensagem);

            Map<String, Object> result = new HashMap<>();
            result.put("nome", nomeExport);
            result.put("bytes", JasperHelper.generateReport(parametros, relatorioDto));
            reports.add(result);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("bytes", Zipper.createZipFile(reports));
        return result;
    }

    public String obterSiglaDisciplina(List<Aula> aulas){

        String mensagem = "";
        List<Disciplina> disciplinas = new ArrayList<>();

        for(Aula aula : aulas){
            if(!disciplinas.contains(aula.getAlocacao().getDisciplina())){

                mensagem = mensagem + "\t" + aula.getAlocacao().getDisciplina().getSigla() + " - " + aula.getAlocacao().getDisciplina().getNome() + ".\n";
                mensagem = mensagem + "\tProfessor da Disciplina: " + aula.getAlocacao().getProfessor1().getNome() + " (" + aula.getAlocacao().getProfessor1().obterNomeAbreviado() + ").\n";

                if(aula.getAlocacao().getDisciplina().getTipo().equalsIgnoreCase("ESPECIAL")){
                    mensagem = mensagem + "\tProfessor da Disciplina: " + aula.getAlocacao().getProfessor2().getNome() + " (" + aula.getAlocacao().getProfessor2().obterNomeAbreviado() + ").\n";
                }

                mensagem = mensagem + "\n";
                disciplinas.add(aula.getAlocacao().getDisciplina());
            }
        }

        return mensagem;
    }
}
