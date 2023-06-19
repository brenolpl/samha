package com.samha.application.professor;

import com.samha.application.turma.AtualizarTurmasAtivas;
import com.samha.commons.UseCase;
import com.samha.domain.Professor;
import com.samha.domain.dto.AulaDto;
import com.samha.domain.dto.RelatorioDto;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.util.JasperHelper;
import com.samha.util.Zipper;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Async
public class GerarRelatorioProfessor extends UseCase<Map<String, Object>> {

    private RelatorioDto relatorioDto;

    @Inject
    public GerarRelatorioProfessor(RelatorioDto relatorioDto) {
        this.relatorioDto = relatorioDto;
        this.addBefore(new AtualizarTurmasAtivas());
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Map<String, Object> execute() throws Exception {
        ObterAulasProfessores obterAulasProfessores = new ObterAulasProfessores(relatorioDto);
        obterAulasProfessores.setGenericRepository(genericRepository);
        List<Professor> professors = obterAulasProfessores.execute().stream().sorted(Comparator.comparing(Professor::getNome)).collect(Collectors.toList());
        List<Map<String, Object>> reports = new ArrayList<>();
        for (var prof : professors) {
            Map<String, String> parametros = gerarHashProfessor(prof);
            parametros.putAll(this.preencherAulas(prof));
            Map<String, Object> arquivo = new HashMap<>();
            arquivo.put("nome", prof.getNome());
            arquivo.put("bytes", JasperHelper.generateReport(parametros, relatorioDto));
            reports.add(arquivo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("bytes", Zipper.createZipFile(reports));
        return result;
    }

    public Byte[] toObjectArray(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        Byte[] objectArray = new Byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            objectArray[i] = byteArray[i];
        }

        return objectArray;
    }

    private Map<String, String> preencherAulas(Professor prof) {
        Map<String, String> hashMapAulas = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 16; j++) {
                final int line = i;
                final int column = j;
                Optional<AulaDto> aulaMatriz = prof.getAulas().stream().filter(a -> a.getDia() == line && a.getNumero() == column).findFirst();
                if (aulaMatriz.isPresent()) {
                    AulaDto aula = aulaMatriz.get();
                    String key = aula.getDia() + String.valueOf(aula.getNumero());
                    String turma = aula.getNomeTurma();
                    String sigla = aula.getNomeDisciplina();
                    hashMapAulas.put(key, turma + "\n" + sigla);
                } else {
                    String key = line + String.valueOf(column);
                    String turma = "";
                    String sigla = "";
                    hashMapAulas.put(key, turma + "\n" + sigla);
                }
            }
        }
        return hashMapAulas;
    }

    public Map<String, String> gerarHashProfessor(Professor professor){

        Map<String, String> hash = new HashMap();

        String anoSemestre = relatorioDto.getAno() + "/" + relatorioDto.getSemestre();
        hash.put("nome", professor.getNome());
        hash.put("setor", professor.getCoordenadoria().getNome());
        hash.put("aulas", professor.getCargaHoraria().intValue() + " aulas semanais" + "\t" + anoSemestre);

        return hash;
    }
}
