package com.brenoleal.samha.domain.legacy;

import com.brenoleal.samha.core.Coordenadoria;
import com.brenoleal.samha.core.Curso;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GtCurso {

    private GtPrincipal gtPrincipal;

    public GtCurso(GtPrincipal gt) {
        gtPrincipal = gt;
    }
    
    public String cadastrar(String nome, String nivel, int periodos, Coordenadoria coordenadoria) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, nivel, periodos, coordenadoria);
            Curso curso = new Curso();
            curso.setNome(nome.toUpperCase());
            curso.setNivel(nivel.toUpperCase());
            curso.setQtPeriodos(periodos);
            curso.setCoordenadoria(coordenadoria);
            gtPrincipal.getGdPrincipal().getGdCurso().cadastrar(curso);

            return Constantes.CADASTRADO;
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String alterar(Curso curso, String nome, String nivel, int periodos, Coordenadoria coordenadoria){
        
        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, nivel, periodos, coordenadoria);
            curso.setNome(nome.toUpperCase());
            curso.setNivel(nivel);
            curso.setQtPeriodos(periodos);
            curso.setCoordenadoria(coordenadoria);
            
            gtPrincipal.getGdPrincipal().getGdCurso().alterar(curso);
       
            return Constantes.ALTERADO;
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public List<Curso> buscar(String coluna, String texto) {
        
        List lista;
        
        if(coluna.toLowerCase().equals("nome"))
            lista = gtPrincipal.getGdPrincipal().getGdCurso().buscar(coluna.toLowerCase(), texto);
        else
            lista = gtPrincipal.getGdPrincipal().getGdCurso().buscarPorNivel(coluna.toLowerCase(), texto);
        
        Collections.sort(lista);
        return lista;
    }
    
    public List<Curso> listar() {
        
        List lista = gtPrincipal.getGdPrincipal().getGdCurso().consultar(Curso.class);
        Collections.sort(lista);
        return lista;
    }
    
    public List<Curso> filtrarPorEixo(int id) {
        
        List lista = gtPrincipal.getGdPrincipal().getGdCurso().filtrarPorEixo(id);
        Collections.sort(lista);
        return lista;
    }
    
    public String excluir(Curso curso) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            List matrizes = gtPrincipal.getGdPrincipal().getGdMatriz().filtrarMatrizCurso(curso.getId());
            
            if (matrizes.isEmpty()) {        
                
                List turmas = gtPrincipal.getGdPrincipal().getGdTurma().filtrarPorCurso(curso.getId());
                
                if (turmas.isEmpty()) {
                    
                    gtPrincipal.getGdPrincipal().getGdCurso().excluir(curso);
                    
                    return Constantes.EXCLUIDO;
                } else {
                    return "Curso possui turmas associadas";
                }
            } else {
                return "Curso possui matrizes associadas";
            }
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public void validarCampos(String nome, String nivel, int periodos, Coordenadoria coordenadoria) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
        
        if(nivel.equals(""))
            throw new SAMHAException(9);
        
        if(periodos <= 0)
            throw new SAMHAException(10);
        
        if(coordenadoria == null)
            throw new SAMHAException(11);
    }
}
