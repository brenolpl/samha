package com.brenoleal.samha.domain.legacy;

import com.brenoleal.samha.core.Eixo;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GtEixo {
    
    private GtPrincipal gtPrincipal;

    public GtEixo(GtPrincipal gt) {
        gtPrincipal = gt;
    }
    
    public String cadastrar(String nome) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome);
            Eixo eixo = new Eixo();
            eixo.setNome(nome.toUpperCase());
            
            gtPrincipal.getGdPrincipal().getGdEixo().cadastrar(eixo);
            return Constantes.CADASTRADO;
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String excluir(Eixo eixo) {

        try {
            gtPrincipal.identificarPermissaoPadrao();
            List coordenadorias = gtPrincipal.getGdPrincipal().getGdCoordenadoria().filtrarCoordenadoriasEixo(eixo.getId());
            
            if(coordenadorias.isEmpty()){
                gtPrincipal.getGdPrincipal().getGdEixo().excluir(eixo);
                return Constantes.EXCLUIDO;
            }else
                return "Eixo possui coordenadorias associadas";
            
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
   
    public List<Eixo> consultar() {
        
        List lista = gtPrincipal.getGdPrincipal().getGdEixo().consultar(Eixo.class);
        Collections.sort(lista);
        return lista;
    }
    
    public void validarCampos(String nome) throws SAMHAException{
        
        if((nome.equals("")))
            throw new SAMHAException(1);
    }
}
