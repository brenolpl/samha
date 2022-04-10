package com.brenoleal.samha.domain.legacy;

import com.brenoleal.samha.core.Curso;
import com.brenoleal.samha.core.MatrizCurricular;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GtMatriz {
    
    private GtPrincipal gtPrincipal;

    public GtMatriz(GtPrincipal gt) {
        gtPrincipal = gt;
    }
    
    public String cadastrar(String nome, int ano, int semestre, Curso curso) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, curso);
            MatrizCurricular matriz = new MatrizCurricular();
            matriz.setNome(nome.toUpperCase());
            matriz.setAno(ano);
            matriz.setCurso(curso);
            matriz.setSemestre(semestre);
            
            gtPrincipal.getGdPrincipal().getGdMatriz().cadastrar(matriz);
            return Constantes.CADASTRADO;
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String excluir(MatrizCurricular matriz) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            List disciplinas = gtPrincipal.getGdPrincipal().getGdDisciplina().filtrarPorMatriz("matriz.id", matriz.getId());
            
            if(disciplinas.isEmpty()){
                gtPrincipal.getGdPrincipal().getGdMatriz().excluir(matriz);
                return Constantes.EXCLUIDO;
                 
            }else
                return "Matriz possui disciplinas associadas";
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public List<MatrizCurricular> filtrarMatrizCurso(int id) {
        
        List lista = gtPrincipal.getGdPrincipal().getGdMatriz().filtrarMatrizCurso(id);
        Collections.sort(lista);
        return lista;
    }
    
    public void validarCampos(String nome, Curso curso) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
        
        if(curso == null)
            throw new SAMHAException(8);
    }     
}
