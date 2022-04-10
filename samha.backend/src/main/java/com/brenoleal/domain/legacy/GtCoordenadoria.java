package com.brenoleal.domain.legacy;

import com.brenoleal.core.Coordenadoria;
import com.brenoleal.core.Curso;
import com.brenoleal.core.Eixo;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GtCoordenadoria {
    
    private GtPrincipal gtPrincipal;

    public GtCoordenadoria(GtPrincipal gt) {    
        gtPrincipal = gt;
    }
    
    public String cadastrar(String nome, Eixo eixo) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, eixo);
            Coordenadoria coordenadoria = new Coordenadoria();
            coordenadoria.setNome(nome.toUpperCase());
            coordenadoria.setEixo(eixo);
            
            gtPrincipal.getGdPrincipal().getGdCoordenadoria().cadastrar(coordenadoria);
            return Constantes.CADASTRADO;
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String excluir(Coordenadoria coordenadoria) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            Curso curso = gtPrincipal.getGdPrincipal().getGdCurso().filtrarCursoUnico("coordenadoria.id", coordenadoria.getId());
           
            if(curso == null){
                                     
                List listaProfessores = gtPrincipal.getGdPrincipal().getGdProfessor().filtrarPorCoordenadoria(coordenadoria.getId());

                if(listaProfessores.isEmpty()){
                    
                    gtPrincipal.getGdPrincipal().getGdCoordenadoria().excluir(coordenadoria);
                    return Constantes.EXCLUIDO;

                }else
                    return "Coordenadoria possui professores associados";                    

            }else
                return "Coordenadoria possui curso associado";
                
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public List<Coordenadoria> buscar(String coluna, String texto) {
        
        List lista = gtPrincipal.getGdPrincipal().getGdCoordenadoria().buscar(coluna.toLowerCase(), texto);
        Collections.sort(lista);
        return lista;
    }
    
    public List<Coordenadoria> filtrarCoordenadoriasEixo(int id) {
        List lista = gtPrincipal.getGdPrincipal().getGdCoordenadoria().filtrarCoordenadoriasEixo(id);
        Collections.sort(lista);
        return lista;
    }

    public List<Coordenadoria> listar() {
        List lista = gtPrincipal.getGdPrincipal().getGdCoordenadoria().consultar(Coordenadoria.class);
        Collections.sort(lista);
        return lista;
    }

    public void validarCampos(String nome, Eixo eixo) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
        
        if(eixo == null)
            throw new SAMHAException(8);
    }       
}
