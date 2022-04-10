package com.brenoleal.domain.legacy;

import com.brenoleal.core.Disciplina;
import com.brenoleal.core.MatrizCurricular;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GtDisciplina {
    
    private GtPrincipal gtPrincipal;
    
    public GtDisciplina(GtPrincipal gt){
        gtPrincipal = gt;
    }
      
    public String cadastrar(String nome, String tipo, int periodo, int cargaHoraria, int qtAulas, MatrizCurricular matriz, String sigla) {

        try {
            
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, tipo, periodo, cargaHoraria, qtAulas, matriz, sigla);
            Disciplina disciplina = new Disciplina();
            
            disciplina.setCargaHoraria(cargaHoraria);
            disciplina.setMatriz(matriz);
            disciplina.setNome(nome);
            disciplina.setPeriodo(periodo);
            disciplina.setQtAulas(qtAulas);
            disciplina.setTipo(tipo.toUpperCase());
            disciplina.setSigla(sigla.toUpperCase());
            
            gtPrincipal.getGdPrincipal().getGdDisciplina().cadastrar(disciplina);           
            return Constantes.CADASTRADO;
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String alterar(String nome, String tipo, int periodo, int cargaHoraria, int qtAulas, MatrizCurricular matriz, Disciplina disciplina, String sigla){
        
        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, tipo, periodo, cargaHoraria, qtAulas, matriz, sigla);
            disciplina.setCargaHoraria(cargaHoraria);
            disciplina.setMatriz(matriz);
            disciplina.setNome(nome);
            disciplina.setPeriodo(periodo);
            disciplina.setQtAulas(qtAulas);
            disciplina.setTipo(tipo.toUpperCase());
            disciplina.setSigla(sigla.toUpperCase());
            
            gtPrincipal.getGdPrincipal().getGdDisciplina().alterar(disciplina);
       
            return Constantes.ALTERADO;
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public List<Disciplina> filtrarPorMatrizPeriodo(String coluna, int idMatriz, int periodo) {
        List lista = gtPrincipal.getGdPrincipal().getGdDisciplina().filtrarPorMatrizPeriodo(idMatriz, periodo);
        Collections.sort(lista);
        return lista;
    }
    
    public List filtrarPorTipoPeriodo(String tipo, int idMatriz, int periodo){
        
        List lista = gtPrincipal.getGdPrincipal().getGdDisciplina().filtrarPorTipoPeriodo(tipo.toUpperCase(), idMatriz, periodo);
        Collections.sort(lista);
        return lista;
    }
    
    public List filtrarPorMatrizPeriodo(int matriz, int periodo){
        
        List lista = gtPrincipal.getGdPrincipal().getGdDisciplina().filtrarPorMatrizPeriodo(matriz, periodo);
        Collections.sort(lista);
        return lista;
    }
    
    public String excluir(Disciplina disciplina) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            List listaAlocacoes = gtPrincipal.getGdPrincipal().getGdAlocacao().filtrarPorDisciplina(disciplina.getId()); 
            
            if(listaAlocacoes.isEmpty()){
                gtPrincipal.getGdPrincipal().getGdDisciplina().excluir(disciplina);
                return Constantes.EXCLUIDO;
            }else
                return "Disciplina está associada a uma alocação";
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public void validarCampos(String nome, String tipo, int periodos, int cargaHoraria, int qtAulas, MatrizCurricular matriz, String sigla) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
        
        if(tipo.equals(""))
            throw new SAMHAException(16);
        
        if((sigla.equals("")))
            throw new SAMHAException(25);
        
        if(periodos <= 0)
            throw new SAMHAException(10);
        
        if(cargaHoraria <= 0)
            throw new SAMHAException(5);
        
        if(qtAulas <= 0)
            throw new SAMHAException(14);
        
        if(matriz == null)
            throw new SAMHAException(13);
    }
}
