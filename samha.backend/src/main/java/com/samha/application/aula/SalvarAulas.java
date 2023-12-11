package com.samha.application.aula;

import com.samha.commons.UseCase;
import com.samha.domain.Alocacao;
import com.samha.domain.Alocacao_;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Eixo;
import com.samha.domain.Oferta;
import com.samha.domain.Oferta_;
import com.samha.domain.Turma_;
import com.samha.domain.dto.OfertaDto;
import com.samha.persistence.IAlocacaoRepository;
import com.samha.persistence.IAulaRepository;
import com.samha.persistence.IOfertaRepository;
import com.samha.persistence.generics.IGenericRepository;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalvarAulas extends UseCase<List<Aula>> {

    private OfertaDto ofertaDto;
    private Oferta oferta;

    @Inject
    public SalvarAulas(OfertaDto ofertaDto) {
        this.ofertaDto = ofertaDto;
    }


    @Inject
    private IGenericRepository genericRepository;

    @Inject
    private IAulaRepository aulaRepository;

    @Inject
    private IOfertaRepository ofertaRepository;

    @Inject
    private IAlocacaoRepository alocacaoRepository;

    @Override
    protected List<Aula> execute() throws Exception {
        this.oferta = genericRepository.get(Oferta.class, ofertaDto.getOfertaId());
        final boolean isCursoAnual = !oferta.getTurma().getMatriz().getCurso().getSemestral();

        List<Aula> aulasBanco = new ArrayList<>();

        if (oferta != null) {
            aulasBanco = genericRepository.find(Aula.class, q -> q.where(
                    q.equal(q.get(Aula_.oferta).get(Oferta_.id), oferta.getId())));
        }

        aulasBanco.stream().forEach(a -> {
            Optional<Aula> aula = ofertaDto.getAulas().stream().filter(a2 -> a2.getId() != null).filter(a1 -> a1.getId().equals(a.getId())).findFirst();
            if (aula.isPresent()) {
                Aula aulaEncontrada = aula.get();
                boolean aulaMudou = this.verificarMudancaAula(a, aulaEncontrada);
                //Se a turma for de ensino médio e o professor estiver alocando as aulas no primeiro período, (ano/1) replicamos as aulas para o período seguinte.
                if (aulaMudou && isCursoAnual && oferta.getSemestre() != 2) replicarMudancasAula(a, aulaEncontrada);
                if (aulaMudou) aulaRepository.save(aulaEncontrada);
                if (!aulaMudou && oferta.getSemestre() == 1 && isCursoAnual) this.verificarExisteAulaProxSemestre(aulaEncontrada);
            } else {
                if (isCursoAnual && oferta.getSemestre() != 2) this.deleteAulaReplicada(a);
                genericRepository.delete(a);
            }
        });

        List<Aula> novasAulas = ofertaDto.getAulas().stream().filter(a -> a.getId() == null).collect(Collectors.toList());
        if (isCursoAnual && !novasAulas.isEmpty() && oferta.getSemestre() != 2) novasAulas.addAll(replicarAulaSemestreSeguinte());
        aulaRepository.saveAll(novasAulas);

        this.atualizarCargaHorariaProfessores();

        return aulaRepository.getAulasByOferta_Id(oferta.getId());
    }

    private void verificarExisteAulaProxSemestre(Aula aulaEncontrada) {
        Aula aulaProxSemestre = findAulaIgualSegundoSemestre(aulaEncontrada);
        if (aulaProxSemestre == null) {
            Aula mesmoHorario = genericRepository.findSingle(Aula.class, q -> q.where(
                    q.equal(q.get(Aula_.numero), aulaEncontrada.getNumero()),
                    q.equal(q.get(Aula_.dia), aulaEncontrada.getDia()),
                    q.equal(q.get(Aula_.turno), aulaEncontrada.getTurno()),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.ano), aulaEncontrada.getOferta().getAno()),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), 2),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.turma), aulaEncontrada.getOferta().getTurma())
            ));
            if (mesmoHorario != null) genericRepository.delete(mesmoHorario);

            this.criarCopiaAula(getOfertaProxSemestre(aulaEncontrada), getAlocacaoProxSemestre(aulaEncontrada), aulaEncontrada.getNumero(), aulaEncontrada.getDia(), aulaEncontrada.getTurno());
        }
    }

    private void replicarMudancasAula(Aula a, Aula aulaEncontrada) {
        Aula aulaIgual = findAulaIgualSegundoSemestre(a);
        if (aulaIgual != null) {
            aulaIgual.setDia(aulaEncontrada.getDia());
            aulaIgual.setNumero(aulaEncontrada.getNumero());
            aulaRepository.save(aulaIgual);
        } else {
            Aula mesmoHorario = genericRepository.findSingle(Aula.class, q -> q.where(
                    q.equal(q.get(Aula_.numero), aulaEncontrada.getNumero()),
                    q.equal(q.get(Aula_.dia), aulaEncontrada.getDia()),
                    q.equal(q.get(Aula_.turno), aulaEncontrada.getTurno()),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.ano), a.getOferta().getAno()),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), 2),
                    q.equal(q.get(Aula_.oferta).get(Oferta_.turma), a.getOferta().getTurma())
            ));
            if (mesmoHorario != null) genericRepository.delete(mesmoHorario);

            Oferta oferta = getOfertaProxSemestre(a);
            Alocacao alocacaoReplicada = getAlocacaoProxSemestre(a);

            this.criarCopiaAula(oferta, alocacaoReplicada, aulaEncontrada.getNumero(), aulaEncontrada.getDia(), aulaEncontrada.getTurno());
        }
    }

    private void criarCopiaAula(Oferta oferta, Alocacao alocacao, Integer numero, Integer dia, Integer turno) {
        Aula aula = new Aula();
        aula.setOferta(oferta);
        aula.setAlocacao(alocacao);
        aula.setTurno(turno);
        aula.setNumero(numero);
        aula.setDia(dia);

        genericRepository.save(aula);
    }

    private void deleteAulaReplicada(Aula a) {
        Aula aulaReplicada = findAulaIgualSegundoSemestre(a);
        if (aulaReplicada != null) aulaRepository.delete(aulaReplicada);
    }

    private Aula findAulaIgualSegundoSemestre(Aula a) {
        return genericRepository.findSingle(Aula.class, q -> q.where(
                q.equal(q.get(Aula_.numero), a.getNumero()),
                q.equal(q.get(Aula_.dia), a.getDia()),
                q.equal(q.get(Aula_.turno), a.getTurno()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.ano), a.getOferta().getAno()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), 2),
                q.equal(q.get(Aula_.oferta).get(Oferta_.turma), a.getOferta().getTurma()),
                q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor1), a.getAlocacao().getProfessor1()),
                q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor2), a.getAlocacao().getProfessor2()),
                q.equal(q.get(Aula_.alocacao).get(Alocacao_.disciplina), a.getAlocacao().getDisciplina())
        ));
    }

    private List<Aula> replicarAulaSemestreSeguinte() {
        List<Aula> aulasReplicadas = new ArrayList<>();
        for (var aula : ofertaDto.getAulas()) {
            Oferta oferta = getOfertaProxSemestre(aula);
            Alocacao alocacaoReplicada = getAlocacaoProxSemestre(aula);

            Aula aulaReplicada = new Aula();
            aulaReplicada.setOferta(oferta);
            aulaReplicada.setAlocacao(alocacaoReplicada);
            aulaReplicada.setTurno(aula.getTurno());
            aulaReplicada.setNumero(aula.getNumero());
            aulaReplicada.setDia(aula.getDia());
            aulasReplicadas.add(aulaReplicada);
        }
        return aulasReplicadas;
    }

    private Alocacao getAlocacaoProxSemestre(Aula aula) {
        Alocacao alocacao = alocacaoRepository.findById(aula.getAlocacao().getId()).get();
        Alocacao alocacaoReplicada = genericRepository.findSingle(Alocacao.class, q -> q.where(
                q.equal(q.get(Alocacao_.ano), aula.getOferta().getAno()),
                q.equal(q.get(Alocacao_.semestre), 2),
                q.equal(q.get(Alocacao_.disciplina), alocacao.getDisciplina()),
                q.equal(q.get(Alocacao_.professor1), alocacao.getProfessor1()),
                q.equal(q.get(Alocacao_.professor2), alocacao.getProfessor2())
        ));
        if (alocacaoReplicada != null) {
            return alocacaoReplicada;
        } else {
            Alocacao novaAlocacao = new Alocacao();
            novaAlocacao.setProfessor1(alocacao.getProfessor1());
            novaAlocacao.setProfessor2(alocacao.getProfessor2());
            novaAlocacao.setTurma(alocacao.getTurma());
            novaAlocacao.setDisciplina(alocacao.getDisciplina());
            novaAlocacao.setAno(aula.getOferta().getAno());
            novaAlocacao.setSemestre(2);
            return alocacaoRepository.saveAndFlush(novaAlocacao);
        }
    }

    private Oferta getOfertaProxSemestre(Aula aula) {
        Oferta oferta = genericRepository.findSingle(Oferta.class, q -> q.where(
                q.equal(q.get(Oferta_.ano), aula.getOferta().getAno()),
                q.equal(q.get(Oferta_.semestre), 2),
                q.equal(q.get(Oferta_.turma).get(Turma_.id), aula.getOferta().getTurma().getId()),
                q.equal(q.get(Oferta_.intervaloMinimo), aula.getOferta().getIntervaloMinimo()),
                q.equal(q.get(Oferta_.tempoMaximoTrabalho), aula.getOferta().getTempoMaximoTrabalho())
        ));
        if (oferta != null) {
            return oferta;
        } else {
            Oferta novaOferta = new Oferta();
            novaOferta.setTurma(aula.getOferta().getTurma());
            novaOferta.setAno(aula.getOferta().getAno());
            novaOferta.setSemestre(2);
            novaOferta.setIntervaloMinimo((int) aula.getOferta().getIntervaloMinimo());
            novaOferta.setTempoMaximoTrabalho(((int)aula.getOferta().getTempoMaximoTrabalho()));
            novaOferta.setPublica(false);
            return ofertaRepository.saveAndFlush(novaOferta);
        }
    }

    @Async
    public void atualizarCargaHorariaProfessores() {
        List<Eixo> eixos = genericRepository.findAll(Eixo.class);
        for (var eixo : eixos) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("ano", new Integer(oferta.getAno()).toString());
            parameters.put("semestre", new Integer(oferta.getSemestre()).toString());
            parameters.put("eixoId", eixo.getId().toString());
//            //atualiza a carga horária de cada professor do eixo informado.
//            this.addAfter(new ObterCargaHoraria(genericRepository, parameters));
        }
    }

    private boolean verificarMudancaAula(Aula a1, Aula a2) {
        return a2.getDia() != a1.getDia() || a2.getNumero() != a1.getNumero();
    }
}
