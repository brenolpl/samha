package com.samha.application.aula;

import com.samha.commons.CorEnum;
import com.samha.commons.UseCase;
import com.samha.domain.Alocacao_;
import com.samha.domain.Aula;
import com.samha.domain.Aula_;
import com.samha.domain.Oferta_;
import com.samha.domain.Professor;
import com.samha.domain.Professor_;
import com.samha.domain.RestricaoProfessor;
import com.samha.domain.RestricaoProfessor_;
import com.samha.domain.dto.Conflito;
import com.samha.domain.dto.Mensagem;
import com.samha.domain.dto.RestricaoRequest;
import com.samha.persistence.generics.IGenericRepository;
import com.samha.util.Horarios;

import javax.inject.Inject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.samha.util.Horarios.obterStringDia;

public class ObterRestricoesAulas extends UseCase<List<Conflito>> {
    private RestricaoRequest restricaoRequest;

    private List<Conflito> conflitos = new ArrayList<>();

    public ObterRestricoesAulas(RestricaoRequest restricaoRequest) {
        this.restricaoRequest = restricaoRequest;
    }

    @Inject
    public ObterRestricoesAulas(RestricaoRequest restricaoRequest, IGenericRepository genericRepository) {
        this.restricaoRequest = restricaoRequest;
        this.genericRepository = genericRepository;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<Conflito> execute() throws Exception {
        List<Aula> aulas = restricaoRequest.getAulas().stream().sorted((a,b) -> {
            if (a.getNumero() > b.getNumero()) return 1;
            else if (a.getNumero() < b.getNumero()) return -1;
            else return 0;
        }).collect(Collectors.toList());
        for (Aula aula : aulas) {
            setConflitoProfessorTurma(aula, aula.getAlocacao().getProfessor1());
            setConflitoIntervaloMinimoTempoMaximoProfessor(aula, aula.getAlocacao().getProfessor1());
            setConflitoRestricaoProfessor(aula, aula.getAlocacao().getProfessor1());
            setConflitoTurnosProfessor(aula, aula.getAlocacao().getProfessor1());
            if (aula.getAlocacao().getDisciplina().getTipo().equalsIgnoreCase("especial") && aula.getAlocacao().getProfessor2() != null) {
                setConflitoProfessorTurma(aula, aula.getAlocacao().getProfessor2());
                setConflitoIntervaloMinimoTempoMaximoProfessor(aula, aula.getAlocacao().getProfessor2());
                setConflitoRestricaoProfessor(aula, aula.getAlocacao().getProfessor2());
                setConflitoTurnosProfessor(aula, aula.getAlocacao().getProfessor2());
            }

        }


        return conflitos.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void setConflitoTurnosProfessor(Aula aula, Professor professor) {
        List<Aula> aulas = restricaoRequest.getAulas();
        aulas.addAll(getAulasOutrasTurmasProfessor(professor));
        switch (aula.getTurno()) {
            case 0:
                if (aula.getNumero() == 4 || aula.getNumero() == 5) {
                    Optional<Aula> ultimaAulaMatutino = aulas.stream().filter(a -> filterAula(a, aula, 5)).findFirst();
                    Optional<Aula> primeiraAulaVespertino = aulas.stream().filter(a -> filterAula(a, aula, 6)).findFirst();
                    if (aula.getNumero() == 4) {
                        if (ultimaAulaMatutino.isPresent()) {
                            if (primeiraAulaVespertino.isPresent()) {
                                this.montarMensagemConflitoIntervaloTurno(aula, primeiraAulaVespertino.get(), professor);
                            }
                        }
                    } else {
                        if (primeiraAulaVespertino.isPresent()) {
                            this.montarMensagemConflitoIntervaloTurno(aula, primeiraAulaVespertino.get(), professor);
                        }
                    }
                }
                Optional<Aula> aulaPulouTurno = aulas.stream().filter(a -> a.getDia() == aula.getDia() && a.getTurno() == 12 && getProfessorMatch(a, aula)).findFirst();
                if(aulaPulouTurno.isPresent()) {
                    this.montarMensagemAulaPulouTurno(aula, aulaPulouTurno.get(), professor);
                }
                break;
            case 6:
                if (aula.getNumero() == 10 || aula.getNumero() == 11) {
                    Optional<Aula> ultimaAulaVespertino = aulas.stream().filter(a -> a.getDia() == aula.getDia() && a.getNumero() == 11 && getProfessorMatch(a, aula)).findFirst();
                    Optional<Aula> ultimaAulaNoturno = aulas.stream().filter(a -> a.getDia() == aula.getDia() && a.getNumero() == 12 && getProfessorMatch(a, aula)).findFirst();
                    if (aula.getNumero() == 10) {
                        if (ultimaAulaVespertino.isPresent()) {
                            if (ultimaAulaNoturno.isPresent()) {
                                this.montarMensagemConflitoIntervaloTurno(aula, ultimaAulaNoturno.get(), professor);
                            }
                        }
                    } else {
                        if (ultimaAulaNoturno.isPresent()) {
                            this.montarMensagemConflitoIntervaloTurno(aula, ultimaAulaNoturno.get(), professor);
                        }
                    }
                }
                break;
            case 12:
                Optional<Aula> possuiAulaMatutina = aulas.stream().filter(a -> a.getDia() == aula.getDia() && a.getTurno() == 0 && getProfessorMatch(a, aula)).findFirst();
                if (possuiAulaMatutina.isPresent()) {
                    this.montarMensagemAulaPulouTurno(possuiAulaMatutina.get(), aula, professor);
                }
        }
    }

    private boolean filterAula(Aula a, Aula aula, Integer numero) {
        boolean aulaMatch = a.getDia() == aula.getDia() && a.getNumero() == numero;
        boolean professorMatch = getProfessorMatch(a, aula);
        return aulaMatch && professorMatch;
    }

    private boolean getProfessorMatch(Aula a, Aula aula) {
        boolean professor1 = aula.getAlocacao().getProfessor1().getId().equals(a.getAlocacao().getProfessor1().getId());
        if (aula.getAlocacao().getProfessor2() != null && a.getAlocacao().getProfessor2() != null) {
            return professor1 && aula.getAlocacao().getProfessor2().getId().equals(a.getAlocacao().getProfessor2().getId());
        } else {
            return professor1;
        }
    }

    private void montarMensagemAulaPulouTurno(Aula primeira, Aula ultima, Professor professor) {
        Mensagem mensagem = new Mensagem();
        mensagem.setTipo(3);
        mensagem.setCor(CorEnum.AZUL.getId());
        mensagem.setTitulo("Restrição de Turno");
        List<String> restricoes = new ArrayList<>();
        restricoes.add("Este professor foi alocado no turno MATUTINO e NOTURNO no mesmo dia");
        String dia = Horarios.obterStringDia(primeira.getDia());
        restricoes.add(dia);
        restricoes.add(primeira.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(primeira.getNumero()));
        restricoes.add(ultima.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(ultima.getNumero()));
        mensagem.setRestricoes(restricoes);
        mensagem.getAulas().add(primeira);
        mensagem.getAulas().add(ultima);
        adicionarConflitoProfessor(professor, mensagem);
    }

    private void adicionarConflitoProfessor(Professor professor, Mensagem mensagem) {
        Optional<Conflito> conflitoOptional = conflitos.stream().filter(c -> c.getProfessor().getId().equals(professor.getId())).findFirst();
        Conflito conflito;
        if (conflitoOptional.isPresent()) {
            conflito = conflitoOptional.get();
        } else {
            conflito = new Conflito();
            conflitos.add(conflito);
        }
        conflito.setProfessor(professor);
        if (verificarMensagemExistente(mensagem, conflito))
            conflito.getMensagens().add(mensagem);
    }

    private void adicionarConflitoProfessor(Professor professor, List<Mensagem> mensagems) {
        mensagems.forEach(m -> adicionarConflitoProfessor(professor, m));
    }

    private void montarMensagemConflitoIntervaloTurno(Aula ultima, Aula primeira, Professor professor) {
        String turnoUltimaAula = this.getTurnoPorAula(ultima.getTurno());
        String turnoPrimeiraAula = this.getTurnoPorAula(primeira.getTurno());
        String dia = Horarios.obterStringDia(ultima.getDia());
        Mensagem mensagem = new Mensagem();
        mensagem.setTitulo("Restrição de turno");
        mensagem.setTipo(2);
        mensagem.setCor(CorEnum.LARANJA.getId());
        List<String> restricoes = new ArrayList<>();
        restricoes.add("Este professor não possui um intervalo entre o turno " + turnoUltimaAula + " e " + turnoPrimeiraAula);
        restricoes.add(dia);
        restricoes.add(ultima.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(ultima.getNumero()));
        restricoes.add(primeira.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(primeira.getNumero()));
        mensagem.getAulas().add(ultima);
        mensagem.getAulas().add(primeira);
        mensagem.setRestricoes(restricoes);
        adicionarConflitoProfessor(professor, mensagem);
    }

    private void setConflitoRestricaoProfessor(Aula aula, Professor professor) {
        final String turno = getTurnoPorAula(aula.getTurno());
        List<RestricaoProfessor> restricoes = genericRepository.find(RestricaoProfessor.class, q -> q.where(
                q.equal(q.get(RestricaoProfessor_.professor).get(Professor_.id), professor.getId()),
                q.equal(q.get(RestricaoProfessor_.turno), turno),
                q.equal(q.get(RestricaoProfessor_.dia), aula.getDia())
        ));

        List<Mensagem> mensagensRestricao = new ArrayList<>();
        boolean resposta;
        for (RestricaoProfessor restricao : restricoes) {
            List<String> restricoesMensagens = new ArrayList<>();
            Mensagem mensagem = new Mensagem();
            mensagem.setTitulo("Restrição de horário");
            setTipoByPrioridadeRestricaoProfessor(restricao, mensagem);

            resposta = identificarNumeroAulaConflitante(restricao, aula.getNumero());

            if (resposta) {
                String dia = Horarios.obterStringDia(aula.getDia());
                restricoesMensagens.add(dia);
                restricoesMensagens.add(aula.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(aula.getNumero()));
                restricoesMensagens.add("Descrição: " + restricao.getNome());
                if (restricao.getDescricao() != null) restricoesMensagens.add(restricao.getDescricao());
                mensagem.setRestricoes(restricoesMensagens);
                mensagensRestricao.add(mensagem);
            }
        }

        if(mensagensRestricao.size() > 0) {
            mensagensRestricao.get(0).getAulas().add(aula);
            adicionarConflitoProfessor(professor, mensagensRestricao);
        }
    }

    private void setTipoByPrioridadeRestricaoProfessor(RestricaoProfessor restricao, Mensagem mensagem) {
        switch (restricao.getPrioridade().toUpperCase()) {
            case "ALTA":
                mensagem.setCor(CorEnum.VERMELHO.getId());
                mensagem.setTipo(1);
                break;
            case "MÉDIA":
                mensagem.setCor(CorEnum.LARANJA.getId());
                mensagem.setTipo(2);
                break;
            default:
                mensagem.setCor(CorEnum.AZUL.getId());
                mensagem.setTipo(3);
        }
    }

    public boolean identificarNumeroAulaConflitante(RestricaoProfessor restricao, int numero){

        String t = restricao.getTurno();
        int turno = getNumeroTurno(t);

        switch(numero - turno){

            case 0: return restricao.isAula1();
            case 1: return restricao.isAula2();
            case 2: return restricao.isAula3();
            case 3: return restricao.isAula4();
            case 4: return restricao.isAula5();
            case 5: return restricao.isAula6();
        }
        return false;
    }

    private String buildMensagemRestricaoAula(int numAula, RestricaoProfessor restricao) {
        return "Aula: " + numAula + " | Restrição: " + restricao.getNome().toUpperCase();
    }

    private String getTurnoPorAula(int turno) {
        switch (turno) {
            case 0:
                return "MATUTINO";
            case 6:
                return "VESPERTINO";
            case 12:
                return "NOTURNO";
            default: return "NAO_MAPEADO";
        }
    }

    public int getNumeroTurno(String turno){

        switch(turno.toUpperCase()){
            case "MATUTINO": return 0;
            case "VESPERTINO": return 6;
            default: return 12;
        }
    }

    private void setConflitoIntervaloMinimoTempoMaximoProfessor(Aula aula, Professor professor) {
        List<Aula> aulasProfessor = restricaoRequest.getAulas().stream().filter(a -> {
            if (a.getAlocacao().getDisciplina().getTipo().equalsIgnoreCase("especial") && a.getAlocacao().getProfessor2() != null) {
                return a.getAlocacao().getProfessor1().getId().equals(professor.getId()) || a.getAlocacao().getProfessor2().getId().equals(professor.getId());
            } else {
                return a.getAlocacao().getProfessor1().getId().equals(professor.getId());
            }
        }).collect(Collectors.toList());

        //pega as aulas de outras turmas do professor
        aulasProfessor.addAll(getAulasOutrasTurmasProfessor(professor));

        aulasProfessor = new ArrayList<>(new HashSet<>(aulasProfessor));

        setConflitoIntervaloMinimoProfessor(aulasProfessor, aula, professor);
        setConflitoTempoMaximoProfessor(aulasProfessor.stream().filter(a -> a.getDia() == aula.getDia()).collect(Collectors.toList()), aula, professor);
    }

    private Collection<? extends Aula> getAulasOutrasTurmasProfessor(Professor professor) {
        return genericRepository.find(Aula.class, q -> q.where(
                        q.equal(q.get(Aula_.oferta).get(Oferta_.ano), restricaoRequest.getOferta().getAno()),
                        q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), restricaoRequest.getOferta().getSemestre()),
                        q.notEqual(q.get(Aula_.oferta).get(Oferta_.id), restricaoRequest.getOferta().getId()),
                        q.or(
                                q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor1).get(Professor_.id), professor.getId()),
                                q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor2).get(Professor_.id), professor.getId())
                        )
                ));
    }

    private void setConflitoIntervaloMinimoProfessor(List<Aula> aulasProfessor, Aula aula, Professor professor) {
        List<Aula> aulaDiaAnterior;
        List<Aula> aulaProxDia;

        if (aula.getDia() != 0) {
            aulaDiaAnterior = aulasProfessor.stream().filter(a -> a.getDia() == aula.getDia() - 1).sorted(Comparator.comparing(Aula::getNumero)).collect(Collectors.toList());
            aulaProxDia = aulasProfessor.stream().filter(a -> a.getDia() == aula.getDia()).sorted(Comparator.comparing(Aula::getNumero)).collect(Collectors.toList());
        } else {
            aulaDiaAnterior = aulasProfessor.stream().filter(a -> a.getDia() == aula.getDia()).sorted(Comparator.comparing(Aula::getNumero)).collect(Collectors.toList());
            aulaProxDia = aulasProfessor.stream().filter(a -> a.getDia() == aula.getDia() + 1).sorted(Comparator.comparing(Aula::getNumero)).collect(Collectors.toList());
        }

        if (!aulaDiaAnterior.isEmpty() && !aulaProxDia.isEmpty()) {
            Aula ultimaAula = aulaDiaAnterior.get(aulaDiaAnterior.size() - 1);
            Aula primeiraAula = aulaProxDia.get(0);
            if(aula.getDia() != 0){
                primeiraAula = aula;
            }
            double tempo = Horarios.obterQuantidadeHoras(primeiraAula, ultimaAula, Horarios.INTERVALO_MINIMO);
            if(tempo< aula.getOferta().getIntervaloMinimo()){
                montarMensagemIntervaloMinimo(ultimaAula, primeiraAula, tempo, professor);
            }
        }
    }

    private void montarMensagemIntervaloMinimo(Aula ultima, Aula primeira, double tempo, Professor professor) {
        Conflito novoConflito;
        Optional<Conflito> conflitoRegistrado = conflitos.stream().filter(c -> c.getProfessor().getId().equals(professor.getId())).findFirst();
        if (conflitoRegistrado.isPresent()) {
            novoConflito = conflitoRegistrado.get();
        } else {
            novoConflito = new Conflito();
            novoConflito.setProfessor(professor);
            conflitos.add(novoConflito);
        }
        String diaAnterior = obterStringDia(ultima.getDia());
        String diaAtual = obterStringDia(primeira.getDia());

        String horarioFinal = Horarios.horarioFinal(ultima.getNumero());
        String horarioInicial = Horarios.horarioInicial(primeira.getNumero());

        Mensagem mensagem = new Mensagem();

        mensagem.setTitulo("Intervalo mínimo de descanso inferior ao permitido: " + Horarios.getTimeFromDouble(tempo).toString() + " horas.");
        mensagem.setCor(CorEnum.VERMELHO.getId());
        mensagem.getAulas().add(ultima);
        mensagem.getAulas().add(primeira);
        mensagem.setTipo(1);

        List<String> restricoes = new ArrayList<>();

        restricoes.add(ultima.getOferta().getTurma().getNome() + ": " + diaAnterior + " - " + horarioFinal + ".");
        restricoes.add(primeira.getOferta().getTurma().getNome() + ": "+ diaAtual + " - " + horarioInicial + ".");

        mensagem.setRestricoes(restricoes);
        if(verificarMensagemExistente(mensagem, novoConflito)) novoConflito.getMensagens().add(mensagem);
    }

    private boolean verificarMensagemExistente(Mensagem mensagem, Conflito novoConflito) {
        Optional<Mensagem> mensagemRegistrada = novoConflito.getMensagens().stream().filter(m -> m.getVerificarMensagem().equals(mensagem.getVerificarMensagem())).findFirst();

        return !mensagemRegistrada.isPresent();
    }

    private void setConflitoTempoMaximoProfessor(List<Aula> aulasProfessor, Aula aula, Professor professor) {
        if (!aulasProfessor.isEmpty()) {
            aulasProfessor = aulasProfessor.stream().sorted(Comparator.comparing(Aula::getNumero)).collect(Collectors.toList());

            if (aula.getTurno() == 0) {
                List<Aula> proximas = aulasProfessor.stream().filter(a -> a.getNumero() > aula.getNumero()).collect(Collectors.toList());
                for (int j = 0; j < proximas.size(); j++) {
                    Aula ultima = proximas.get(j);

                    double tempo = Horarios.obterQuantidadeHoras(aula, ultima, Horarios.TEMPO_MAXIMO);

                    if (tempo > aula.getOferta().getTempoMaximoTrabalho())
                        montarMensagemTempoMaximo(ultima, aula, tempo, professor);
                }
            } else if (aula.getTurno() == 12) {
                List<Aula> anteriores = aulasProfessor.stream().filter(a -> a.getNumero() < aula.getNumero()).collect(Collectors.toList());
                for (int i = 0; i < anteriores.size(); i++) {
                    Aula primeira = anteriores.get(i);

                    double tempo = Horarios.obterQuantidadeHoras(primeira, aula, Horarios.TEMPO_MAXIMO) + Horarios.INTERVALO_MINIMO_ALMOCO;

                    if (tempo> aula.getOferta().getTempoMaximoTrabalho())
                        montarMensagemTempoMaximo(aula, primeira, tempo, professor);
                }
            }

        }
    }

    public void montarMensagemTempoMaximo(Aula ultima, Aula primeira, double tempo, Professor professor) {
        Conflito novoConflito;
        Optional<Conflito> conflito = conflitos.stream().filter(c -> c.getProfessor().getId().equals(professor.getId())).findFirst();
        if(conflito.isPresent()) {
            novoConflito = conflito.get();
        } else {
            novoConflito = new Conflito();
            conflitos.add(novoConflito);
        }

        LocalTime hour = Horarios.getTimeFromDouble(tempo);

        novoConflito.setProfessor(professor);

        Mensagem mensagem = new Mensagem();
        String dia = Horarios.obterStringDia(ultima.getDia());
        mensagem.setTitulo("Tempo máximo de trabalho superior ao permitido: " + hour.toString() + " horas.");
        mensagem.setCor(CorEnum.VERMELHO.getId());
        mensagem.getAulas().add(ultima);
        mensagem.getAulas().add(primeira);
        mensagem.setTipo(1);
        List<String> restricoes = new ArrayList<>();
        restricoes.add(dia);
        restricoes.add(primeira.getOferta().getTurma().getNome() + ": " + Horarios.horarioInicial(primeira.getNumero()));
        restricoes.add(ultima.getOferta().getTurma().getNome() + ": " + Horarios.horarioFinal(ultima.getNumero()));

        mensagem.setRestricoes(restricoes);
        if(verificarMensagemExistente(mensagem, novoConflito)) novoConflito.getMensagens().add(mensagem);
    }



    /**
     * Método que retorna os conflitos de um professor dado uma determinada aula.
     * Um professor não pode estar ao mesmo tempo em duas turmas diferentes, logo, o filtro por oferta é feito abaixo
     *
     * @param aula
     * @return
     */
    private void setConflitoProfessorTurma(Aula aula, Professor professor) {
        List<Aula> aulasProfessor = genericRepository.find(Aula.class, q -> q.where(
                q.equal(q.get(Aula_.numero), aula.getNumero()),
                q.equal(q.get(Aula_.dia), aula.getDia()),
                q.equal(q.get(Aula_.alocacao).get(Alocacao_.ano), aula.getAlocacao().getAno()),
                q.equal(q.get(Aula_.alocacao).get(Alocacao_.semestre), aula.getAlocacao().getSemestre()),
                q.or(
                        q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor1).get(Professor_.id), professor.getId()),
                        q.equal(q.get(Aula_.alocacao).get(Alocacao_.professor2).get(Professor_.id), professor.getId())
                ),
                q.notEqual(q.get(Aula_.oferta).get(Oferta_.id), aula.getOferta().getId()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.ano), aula.getOferta().getAno()),
                q.equal(q.get(Aula_.oferta).get(Oferta_.semestre), aula.getOferta().getSemestre())
        ));

        if (!aulasProfessor.isEmpty()) {
            Mensagem mensagem = new Mensagem();
            mensagem.setTitulo("Está em outra(s) turma(s) neste horário: ");
            mensagem.setCor(CorEnum.VERMELHO.getId());
            mensagem.setTipo(1);
            List<String> restricoes = new ArrayList<>();
            for (Aula restricao : aulasProfessor) {
                restricoes.add(restricao.getOferta().getTurma().getNome() + " - " + restricao.getAlocacao().getDisciplina().getNome() + ". ");
                restricoes.add(Horarios.horarioInicial(aula.getNumero()) + " a " + Horarios.horarioFinal(aula.getNumero()));
                mensagem.getAulas().add(aula);
            }
            mensagem.setRestricoes(restricoes);
            adicionarConflitoProfessor(professor, mensagem);
        }
    }
}