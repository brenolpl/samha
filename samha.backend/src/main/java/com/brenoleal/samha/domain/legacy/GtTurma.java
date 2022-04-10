package com.brenoleal.samha.domain.legacy;

import com.brenoleal.samha.core.MatrizCurricular;
import com.brenoleal.samha.core.Oferta;
import com.brenoleal.samha.core.Turma;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GtTurma {

    private GtPrincipal gtPrincipal;

    public GtTurma(GtPrincipal gt) {
        gtPrincipal = gt;
    }
    
    public String cadastrar(String nome, String turno, int ano, int semestre, MatrizCurricular matriz) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, turno, matriz);
            Turma turma = new Turma();
           
            turma.setAno(ano);
            turma.setMatriz(matriz);
            turma.setNome(nome.toUpperCase());
            turma.setSemestre(semestre);
            turma.setTurno(turno.toUpperCase());
            
            gtPrincipal.getGdPrincipal().getGdTurma().cadastrar(turma);           
            return Constantes.CADASTRADO;
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String alterar(String nome, String turno, int ano, int semestre, MatrizCurricular matriz, Turma turma){
        
        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, turno, matriz);
            turma.setAno(ano);
            turma.setMatriz(matriz);
            turma.setNome(nome.toUpperCase());
            turma.setSemestre(semestre);
            turma.setTurno(turno.toUpperCase());
            
            gtPrincipal.getGdPrincipal().getGdTurma().alterar(turma);
       
            return Constantes.ALTERADO;
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public List<Turma> buscar(String coluna, String texto) {
        
        List lista;
        
        if(coluna.toLowerCase().equals("curso"))
            lista = gtPrincipal.getGdPrincipal().getGdTurma().filtrarPorCurso(Integer.valueOf(texto));
        else
            lista= gtPrincipal.getGdPrincipal().getGdTurma().buscar(coluna.toLowerCase(), texto);
        
        Collections.sort(lista);
        return lista;
    }
    
    public List<Turma> buscarPorCurso(int id) {
        
        List lista = gtPrincipal.getGdPrincipal().getGdTurma().filtrarPorCurso(id);
        Collections.sort(lista);
        return lista;
    }
    
    public List<Turma> buscarPorEixo(int id) {
        
        List lista = gtPrincipal.getGdPrincipal().getGdTurma().filtrarPorEixo(id);
        Collections.sort(lista);
        return lista;
    }
    
    public List listar(){
        List lista = gtPrincipal.getGdPrincipal().getGdTurma().consultar(Turma.class);
        Collections.sort(lista);
        return lista;
    }
    
    public String excluir(Turma turma) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            List<Oferta> listaOfertas = gtPrincipal.getGdPrincipal().getGdOferta().filtrarOfertasTurma(turma.getId());
            
            boolean semAulas = true;
            
            for(Oferta oferta : listaOfertas){
                List aulas = gtPrincipal.getGdPrincipal().getGdAula().filtrarAulasOferta(oferta.getId());
                if(!aulas.isEmpty()){
                    semAulas = false;
                    break;
                }
                gtPrincipal.getGdPrincipal().getGdOferta().excluir(oferta);
            }
            
            if(semAulas){
                gtPrincipal.getGdPrincipal().getGdTurma().excluir(turma);
                return Constantes.EXCLUIDO;
            }else
                return "Turma possui aulas associadas";
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public void validarCampos(String nome, String turno, MatrizCurricular matriz) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
        
        if(turno.equals(""))
            throw new SAMHAException(15);
        
        if(matriz == null)
            throw new SAMHAException(13);
    }
    
    public List filtrarTurmasAtivas(List<Turma> listaTurmas, int ano, int semestre){
        
        List turmasAtivas = new ArrayList();
        
        for(Turma turma : listaTurmas){
            if(verificarTurmaAtiva(turma, ano, semestre)){
                turmasAtivas.add(turma);
            }
        }
        return turmasAtivas;
    }
    
    public boolean verificarTurmaAtiva(Turma turma, int anoAtual, int semestreAtual){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        String p = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO")){
         
            p = calcularAnoAtual(anoAtual, anoInicial);
        }else{

            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
        }
        
        int anoPeriodo = Integer.valueOf(p);
        
        if(anoPeriodo > turma.getMatriz().getCurso().getQtPeriodos() || anoPeriodo < 1)
            return false;
        
        return true;
    }
    
    public String obterStringAnoPeriodoAtual(int anoAtual, int semestreAtual, Turma turma){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        
        String nivel = null;
        String periodoAtual = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO")){
            nivel = "º ANO";
            periodoAtual = calcularAnoAtual(anoAtual, anoInicial);
        }else{
            nivel = "º PERÍODO";
            periodoAtual = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
        }

        return periodoAtual + nivel;
    }
    
    public int obterInteiroAnoSemestreAtual(int anoAtual, int semestreAtual, Turma turma){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        
        String p = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO"))
            p = calcularAnoAtual(anoAtual, anoInicial);
        else
            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
         
        return Integer.valueOf(p);
    }
    
    public String calcularAnoAtual(int anoAtual, int anoInicial){
        
        int qtAnos = (anoAtual - anoInicial) + 1;
        String ano = String.valueOf(qtAnos);
        
        return ano;
    }
    
    public String calcularPeriodoAtual(int anoAtual, int semestreAtual, int anoInicial, int semestreInicial){
        
        int qtAnos = ((anoAtual - anoInicial) * 2);
        
        if(semestreAtual == semestreInicial){
            qtAnos+=1;
        }else if(semestreAtual == 2 && semestreInicial == 1){
            qtAnos+=2;
        }
        
        String periodo = String.valueOf(qtAnos);
        
        return periodo;
    }
    
}
