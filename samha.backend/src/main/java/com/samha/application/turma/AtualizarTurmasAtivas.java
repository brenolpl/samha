package com.samha.application.turma;

import com.samha.commons.UseCase;
import com.samha.domain.Turma;
import com.samha.domain.Turma_;
import com.samha.persistence.generics.IGenericRepository;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;

public class AtualizarTurmasAtivas extends UseCase<Boolean> {

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected Boolean execute() throws Exception {
        List<Turma> turmasAtivas = this.genericRepository.find(Turma.class, q -> q.where(
                q.equal(q.get(Turma_.ativa), true)
        ));

        boolean isAtiva;

        for(var turma : turmasAtivas){
            isAtiva = this.verificarTurmaContinuaAtiva(turma);
            if(isAtiva != turma.getAtiva()){
                turma.setAtiva(isAtiva);
                genericRepository.save(turma);
            }
        }

        return null;
    }

    private boolean verificarTurmaContinuaAtiva(Turma turma) {
        int qtPeriodosCurso = turma.getMatriz().getCurso().getQtPeriodos();
        int semestreAtual = -1;
        if(Calendar.getInstance().get(Calendar.MONTH) >= Calendar.getInstance().get(Calendar.JULY)){
            semestreAtual = 2;
        }else{
            semestreAtual = 1;
        }

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

        int periodo;

        if(!turma.getMatriz().getCurso().getSemestral()){
            periodo = calcularAnoAtual(anoAtual, turma.getAno());
        }else{
            periodo = calcularPeriodoAtual(anoAtual, semestreAtual, turma.getAno(), turma.getSemestre());
        }

        if(periodo > turma.getMatriz().getCurso().getQtPeriodos() || periodo < 1)
            return false;

        return true;
    }

//    public boolean verificarTurmaAtiva(Turma turma){
//
//        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
//
//        int anoInicial = turma.getAno();
//        int semestreInicial = turma.getSemestre();
//        String p = null;
//
//        if(turma.getMatriz().getCurso().getNivel().equalsIgnoreCase("ENSINO MÉDIO INTEGRADO")){
//
//            p = calcularAnoAtual(anoAtual, anoInicial);
//        }else{
//
//            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
//        }
//
//        int anoPeriodo = Integer.valueOf(p);
//
//        if(anoPeriodo > turma.getMatriz().getCurso().getQtPeriodos() || anoPeriodo < 1)
//            return false;
//
//        return true;
//    }

    public int calcularAnoAtual(int anoAtual, int anoInicial){

        int qtAnos = (anoAtual - anoInicial) + 1;
        return qtAnos;
    }

    public int calcularPeriodoAtual(int anoAtual, int semestreAtual, int anoInicial, int semestreInicial){

        int qtAnos = ((anoAtual - anoInicial) * 2);

        if(semestreAtual == semestreInicial){
            qtAnos+=1;
        }else if(semestreAtual == 2 && semestreInicial == 1){
            qtAnos+=2;
        }

        return qtAnos;
    }
}
