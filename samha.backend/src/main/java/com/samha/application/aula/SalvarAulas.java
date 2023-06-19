package com.samha.application.aula;

import com.samha.application.alocacao.ObterCargaHoraria;
import com.samha.commons.UseCase;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Eixo;
import com.samha.domain.Oferta_;
import com.samha.persistence.IAulaRepository;
import com.samha.persistence.generics.IGenericRepository;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalvarAulas extends UseCase<List<Aula>> {

    private List<Aula> aulas;

    @Inject
    public SalvarAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Inject
    private IAulaRepository aulaRepository;

    @Override
    protected List<Aula> execute() throws Exception {
        List<Aula> aulasBanco = genericRepository.find(Aula.class, q -> q.where(
                q.equal(q.get(Aula_.oferta).get(Oferta_.id), aulas.get(0).getOferta().getId())));

        aulasBanco.stream().forEach(a -> {
            Optional<Aula> aula = aulas.stream().filter(a2 -> a2.getId() != null).filter(a1 -> a1.getId().equals(a.getId())).findFirst();
            if (aula.isPresent()) {
                Aula aulaEncontrada = aula.get();
                boolean aulaMudou = this.verificarMudancaAula(a, aulaEncontrada);
                if (aulaMudou) aulaRepository.save(aulaEncontrada);
            } else {
                genericRepository.delete(a);
            }
        });

        List<Aula> novasAulas = aulas.stream().filter(a -> a.getId() == null).collect(Collectors.toList());
        aulaRepository.saveAll(novasAulas);

        this.atualizarCargaHorariaProfessores();

        return aulaRepository.getAulasByOferta_Id(aulas.get(0).getOferta().getId());
    }

    @Async
    public void atualizarCargaHorariaProfessores() {
        List<Eixo> eixos = genericRepository.findAll(Eixo.class);
        for (var eixo : eixos) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("ano", new Integer(aulas.get(0).getOferta().getAno()).toString());
            parameters.put("semestre", new Integer(aulas.get(0).getOferta().getSemestre()).toString());
            parameters.put("eixoId", eixo.getId().toString());
            //atualiza a carga horária de cada professor do eixo informado.
            this.addAfter(new ObterCargaHoraria(genericRepository, parameters));
        }
    }

    private boolean verificarMudancaAula(Aula a1, Aula a2) {
        return a2.getDia() != a1.getDia() || a2.getNumero() != a1.getNumero();
    }
}
